package ru.zeet.fin.dao;

import org.springframework.stereotype.Service;
import ru.zeet.fin.domain.Account;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.exception.CommonServiceException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Service
public class AccountDao implements Dao<Account, Long> {
    private final DataSource dataSource;

    public AccountDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Account> findByAll() {
        return null;
    }

    @Override
    public Account insert(Account account) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(
                        "insert into account (name, user_id, balance) values (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            // ps.setString(1, serviceUser.getName());
            ps.setString(1, account.getName());
            ps.setLong(2, account.getUserId());
            ps.setBigDecimal(3, account.getBalance());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new CommonServiceException("Creating account failed, no rows!");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getLong("id"));
                } else {
                    throw new CommonServiceException("Creating account failed, no id!");
                }
            }
        } catch (SQLException e) {
            throw new CommonServiceException(e);
        }
        return account;
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

    @Override
    public Account findById(Long id) {
        try {
            return findById(id, dataSource.getConnection());
        } catch (SQLException e) {
            throw new CommonServiceException(e);
        }
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
                account.setUserId(rs.getLong("user_id"));
                account.setBalance(rs.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            throw new CommonServiceException(e);
        }
        return account;
    }
}
