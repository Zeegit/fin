package ru.zeet.fin.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.zeet.fin.exception.CommonServiceException;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DaoFactory {
    private static ServiceUserDao serviceUserDao;
    public static ServiceUserDao getServiceUserDao() {
        if (serviceUserDao == null) {
            serviceUserDao = new ServiceUserDao(getDataSource());
        }
        return serviceUserDao;
    }

    private static DataSource dataSource;
    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource ds = new HikariDataSource();
            //ds.setJdbcUrl("jdbc:postgresql://localhost:5432/fin");
            //ds.setUsername("postgres");
            //ds.setPassword("postgres");
            ds.setJdbcUrl(System.getProperty("jdbcUrl", "jdbc:postgresql://localhost:5432/fin"));
            ds.setUsername(System.getProperty("jdbcUsername", "postgres"));
            ds.setPassword(System.getProperty("jdbcPassword", "postgres"));

            dataSource= ds;

            initDataBase();
        }
        return dataSource;
    }

    private static void initDataBase() {
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            Liquibase liquibase = new Liquibase( "liquibase.xml", new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException e) {
            throw new CommonServiceException(e);
        }
    }

    private static TransactionDao transactionDao;
    public static TransactionDao getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = new TransactionDao(getDataSource());
        }
        return transactionDao;
    }

    private static AccountDao accountDao;
    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDao(getDataSource());
        }
        return accountDao;
    }

    //public static Connection getConnection() throws SQLException {
   //     return getDataSource().getConnection();
  //  }

  //  private DaoFactory() {
  //  }
}
