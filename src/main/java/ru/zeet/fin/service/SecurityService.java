package ru.zeet.fin.service;

import ru.zeet.fin.converter.ServiceUserToUserDtoConverter;
import ru.zeet.fin.dao.ServiceUserDao;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.dto.UserDto;

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
}
