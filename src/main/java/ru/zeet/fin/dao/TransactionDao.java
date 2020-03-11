package ru.zeet.fin.dao;

import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.domain.Transaction;
import ru.zeet.fin.exception.CommonServiceException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class TransactionDao implements Dao<Transaction, Long> {
    private final DataSource dataSource;

    public TransactionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Transaction insert(Transaction transaction, Connection connection) {
        try (
                PreparedStatement ps = connection.prepareStatement(
                        "insert into transaction (sum, from_account_id, to_account_id, date) " +
                                "values (? , ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setBigDecimal(1, transaction.getSum());
            ps.setLong(2, transaction.getFromAccountId());
            ps.setLong(3, transaction.getToAccountId());
            ps.setDate(4, transaction.getDate());

            int affectedRows  =ps.executeUpdate();
            if (affectedRows == 0) {
                throw new CommonServiceException("Creating user failed, no rows!");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setId(generatedKeys.getLong("id"));
                } else {
                    throw new CommonServiceException("Creating transaction failed, no id!");
                }
            }
        } catch(SQLException e){
            throw new CommonServiceException(e);
        }
        return transaction;
    }

    @Override
    public Transaction findById(Long aLong) {
        return null;
    }

    @Override
    public List<Transaction> findByAll() {
        return null;
    }

    @Override
    public Transaction insert(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction update(Transaction transaction) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}
