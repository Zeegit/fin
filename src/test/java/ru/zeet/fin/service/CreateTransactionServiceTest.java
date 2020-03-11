package ru.zeet.fin.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.zeet.fin.dao.*;
import ru.zeet.fin.domain.Account;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.domain.Transaction;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class CreateTransactionServiceTest {

    CreateTransactionService subj;

    AccountDao accountDao;
    TransactionDao transactionDao;
    TransactionDao transactionDaoMock;
    DataSource dataSource;
    ServiceUserDao serviceUserDao;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        // subj = ServiceFactory.getCreateTransactionService();
        accountDao = DaoFactory.getAccountDao();
        transactionDao = DaoFactory.getTransactionDao();
        transactionDaoMock = Mockito.mock(TransactionDao.class);
        dataSource = DaoFactory.getDataSource();
        serviceUserDao = DaoFactory.getServiceUserDao();
    }

    @Test
    public void createTransaction_ok() {
        subj = new CreateTransactionService(transactionDao, accountDao, dataSource);

        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setEmail("i@i.ru");
        serviceUser.setPassword("pwd");
        serviceUserDao.insert(serviceUser);

        Account fromAccount = new Account();
        fromAccount.setName("From");
        fromAccount.setUserId(serviceUser.getId());
        fromAccount.setBalance(BigDecimal.valueOf(100));
        accountDao.insert(fromAccount);

        Account toAccount = new Account();
        toAccount.setName("To");
        toAccount.setUserId(serviceUser.getId());
        toAccount.setBalance(BigDecimal.valueOf(0));
        accountDao.insert(toAccount);

        subj.createTransaction(fromAccount.getId(), toAccount.getId(), BigDecimal.valueOf(15));

        assertEquals(BigDecimal.valueOf(85.0), accountDao.findById(fromAccount.getId()).getBalance());
        assertEquals(BigDecimal.valueOf(15.0), accountDao.findById(toAccount.getId()).getBalance());
    }

    @Test
    public void createTransaction_notOk() {
        subj = new CreateTransactionService(transactionDaoMock, accountDao, dataSource);

        // Mockito.when(transactionDaoMock.insert(Mockito.any(Transaction.class))).thenThrow(new SQLException());
        Mockito.doThrow(new RuntimeException()).when(transactionDaoMock).insert(
                Mockito.any(Transaction.class),
                Mockito.any(Connection.class));

        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setEmail("i@i.ru");
        serviceUser.setPassword("pwd");
        serviceUserDao.insert(serviceUser);

        Account fromAccount = new Account();
        fromAccount.setName("From");
        fromAccount.setUserId(serviceUser.getId());
        fromAccount.setBalance(BigDecimal.valueOf(100));
        accountDao.insert(fromAccount);

        Account toAccount = new Account();
        toAccount.setName("To");
        toAccount.setUserId(serviceUser.getId());
        toAccount.setBalance(BigDecimal.valueOf(0));
        accountDao.insert(toAccount);

        try {
            subj.createTransaction(fromAccount.getId(), toAccount.getId(), BigDecimal.valueOf(15));
        } catch (Exception ignored) {}

        assertEquals(BigDecimal.valueOf(100.0), accountDao.findById(fromAccount.getId()).getBalance());
        assertEquals(BigDecimal.valueOf(0.0), accountDao.findById(toAccount.getId()).getBalance());
    }
}
