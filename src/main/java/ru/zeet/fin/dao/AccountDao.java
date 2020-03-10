package ru.zeet.fin.dao;

import ru.zeet.fin.domain.Account;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.exception.CommonServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDao implements Dao<Account, Long> {
    @Override
    public Account findById(Long aLong) {
        return null;
    }

    @Override
    public List<Account> findByAll() {
        return null;
    }

    @Override
    public Account insert(Account account) {
        return null;
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    public Account update(Account account, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update account set name = ?, balance = ? where id = ?");
        ps.setString(1, account.getName());
        ps.setBigDecimal(2, account.getBalance());
        ps.setLong(3, account.getId());

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new CommonServiceException("Update account failed, no rows!");
        }

        return account;
    }

    public Account findById(Long id, Connection connection) throws SQLException {
        Account account = null;

        PreparedStatement ps = connection.prepareStatement("select * from account where id = ?");
        ps.setLong(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                account = new Account();
                account.setId(rs.getLong("id"));
                account.setName(rs.getString("name"));
                account.setFromAccount(rs.getLong("from_account_id"));
                account.setFromAccount(rs.getLong("to_account_id"));
                account.setBalance(rs.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            throw new CommonServiceException(e);
        }
        return account;
    }
}
