package ru.zeet.fin.dao;

import java.sql.*;
import java.util.List;

import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.exception.CommonServiceException;

import javax.sql.DataSource;

public class ServiceUserDao implements Dao<ServiceUser, Long> {
    private final DataSource dataSource;

    public ServiceUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ServiceUser findByEmail(String email) {
        ServiceUser serviceUser = null;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps=connection.prepareStatement("select * from service_user where email = ?")
        ) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    serviceUser = new ServiceUser();
                    serviceUser.setId(rs.getLong("id"));
                    serviceUser.setName(rs.getString("name"));
                    serviceUser.setEmail(rs.getString("email"));
                    serviceUser.setPassword(rs.getString("password"));
                }
            }
        } catch(SQLException e){
            throw new CommonServiceException(e);
        }
        return serviceUser;
    }

    @Override
    public ServiceUser findById(Long id) {
       /* ServiceUser user = null;

        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from public.user where id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new ServiceUser();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        }
        return user;*/
       return null;
    }

    @Override
    public List<ServiceUser> findByAll() {
        return null;
    }

    @Override
    public ServiceUser insert(ServiceUser serviceUser) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps=connection.prepareStatement(
                        "insert into service_user (name, email, password) values (?, ? , ?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, serviceUser.getName());
            ps.setString(2, serviceUser.getEmail());
            ps.setString(3, serviceUser.getPassword());

            int affectedRows  =ps.executeUpdate();
            if (affectedRows == 0) {
                throw new CommonServiceException("Creating user failed, no rows!");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    serviceUser.setId(generatedKeys.getLong("1"));
                } else {
                    throw new CommonServiceException("Creating user failed, no id!");
                }
            }
        } catch(SQLException e){
            throw new CommonServiceException(e);
        }
        return serviceUser;
    }

    @Override
    public ServiceUser update(ServiceUser serviceUser) {
        return null;
    }

    @Override
    public boolean delete(Long integer) {
        return false;
    }


}
