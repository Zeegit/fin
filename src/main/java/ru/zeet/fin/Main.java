package ru.zeet.fin;


import ru.zeet.fin.dto.UserDto;
import ru.zeet.fin.service.SecurityService;
import ru.zeet.fin.service.ServiceFactory;

public class Main {

    public static void main(String[] args)  {
        SecurityService securityService =  ServiceFactory.getSecurityService();
        UserDto userDto = securityService.auth("user@mail.ru", "userpwd");
        System.out.println(userDto);

    }
}
