package ru.zeet.fin.service;

import org.springframework.stereotype.Service;
import ru.zeet.fin.dao.AccountDao;
import ru.zeet.fin.dao.TransactionDao;
import ru.zeet.fin.domain.Account;
import ru.zeet.fin.domain.Transaction;
import ru.zeet.fin.exception.CommonServiceException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@Service
public class CreateTransactionService {
    private final TransactionDao transactionDao;
    private final AccountDao accountDao;
    private final DataSource dataSource;

    public CreateTransactionService(TransactionDao transactionDao, AccountDao accountDao, DataSource dataSource) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
        this.dataSource = dataSource;
    }

    public void createTransaction(Long fromAccountId, Long toAccountId, BigDecimal sum) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            Account fromAccount = accountDao.findById(fromAccountId, connection);
            if (fromAccount == null) {
                throw new CommonServiceException("No account account id: " + fromAccountId);
            }

            Account toAccount = accountDao.findById(toAccountId, connection);
            if (toAccount == null) {
                throw new CommonServiceException("No account account id: " + toAccountId);
            }

            BigDecimal fromAccountBalance = fromAccount.getBalance();
            if (fromAccountBalance.compareTo(sum) < 0) {
                throw new CommonServiceException("No money account id: " + toAccountId);
            }

            fromAccount.setBalance(fromAccountBalance.add(sum.negate()));
            accountDao.update(fromAccount, connection);

            BigDecimal toAccountBalance = toAccount.getBalance();
            toAccount.setBalance(toAccountBalance.add(sum));
            accountDao.update(toAccount, connection);

            Transaction transaction = new Transaction();
            transaction.setFromAccountId(fromAccountId);
            transaction.setToAccountId(toAccountId);
            transaction.setSum(sum);
            transaction.setDate(new Date(System.currentTimeMillis()));
            transactionDao.insert(transaction, connection);

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ignored) {}
            }

            throw new CommonServiceException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }
    }
}
