package ru.zeet.fin.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

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
            ds.setJdbcUrl("jdbc:postgresql://localhost:5432/fin");
            ds.setUsername("postgres");
            ds.setPassword("postgres");

            dataSource= ds;
        }
        return dataSource;
    }

    //public static Connection getConnection() throws SQLException {
   //     return getDataSource().getConnection();
  //  }

  //  private DaoFactory() {
  //  }
}
