package ru.zeet.fin.service;

import ru.zeet.fin.converter.ServiceUserToUserDtoConverter;
import ru.zeet.fin.dao.ServiceUserDao;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.dto.UserDto;
import ru.zeet.fin.exception.CommonServiceException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

public class SecurityService {
    private final ServiceUserDao serviceUserDao;
    private final DigestService digestService;
    private final ServiceUserToUserDtoConverter serviceUserToUserDtoConverter;

    public SecurityService(ServiceUserDao serviceUserDao, DigestService digestService, ServiceUserToUserDtoConverter serviceUserToUserDtoConverter) {
        this.serviceUserDao = serviceUserDao;
        this.digestService = digestService;
        this.serviceUserToUserDtoConverter = serviceUserToUserDtoConverter;
    }

    public UserDto auth(String email, String passwoed) {
        ServiceUser serviceUser = serviceUserDao.findByEmail(email);
        if (serviceUser != null) {
            String passwordHash = digestService.hash(passwoed);
            if (passwordHash.equals(serviceUser.getPassword())) {
                return serviceUserToUserDtoConverter.convert(serviceUser);
            }
        }
        return null;
    }

    public UserDto register(String email, String password) {

        ServiceUser serviceUser = serviceUserDao.findByEmail(email);
        if (serviceUser == null) {

            serviceUser = new ServiceUser();
            serviceUser.setEmail(email);
            serviceUser.setPassword(digestService.hash(password));
            serviceUserDao.insert(serviceUser);

                /*try {
                    authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                } catch (AuthenticationException e) {
                    throw new CustomException(e);
                }
                return (CustomUserDetails) authentication.getPrincipal();*/
        }

        return serviceUserToUserDtoConverter.convert(serviceUser);
    }
}
