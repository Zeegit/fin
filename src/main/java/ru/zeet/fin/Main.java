package ru.zeet.fin;


import ru.zeet.fin.dao.DaoFactory;
import ru.zeet.fin.dao.ServiceUserDao;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.dto.UserDto;
import ru.zeet.fin.service.SecurityService;
import ru.zeet.fin.service.ServiceFactory;

public class Main {

    public static void main(String[] args)  {
        //SecurityService securityService =  ServiceFactory.getSecurityService();
        //UserDto userDto = securityService.auth("user@mail.ru", "userpwd");
        //S/ystem.out.println(userDto);

        ServiceUserDao serviceUserDao = DaoFactory.getServiceUserDao();

        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setEmail("i@i.ru");
        serviceUser.setPassword("pwd");
        serviceUserDao.insert(serviceUser);

        System.out.println(serviceUser);


    }
}
