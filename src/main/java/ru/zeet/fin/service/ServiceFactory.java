package ru.zeet.fin.service;

import ru.zeet.fin.converter.ServiceUserToUserDtoConverter;
import ru.zeet.fin.dao.DaoFactory;

public class ServiceFactory {
    private static ServiceUserToUserDtoConverter serviceUserToUserDtoConverter;

    public static ServiceUserToUserDtoConverter getserviceUserToUserDtoConverter() {
        if (serviceUserToUserDtoConverter == null) {
            serviceUserToUserDtoConverter = new ServiceUserToUserDtoConverter();
        }
        return serviceUserToUserDtoConverter;
    }

    private static DigestService digestService;

    public static DigestService getDigestService() {
        if (digestService == null) {
            digestService = new DigestService();
        }
        return digestService;
    }

    private static SecurityService securityService;

    public static SecurityService getSecurityService() {
        if (securityService == null) {
            securityService = new SecurityService(
                    DaoFactory.getServiceUserDao(),
                    getDigestService(),
                    getserviceUserToUserDtoConverter());
        }
        return securityService;
    }
}
