package ru.zeet.fin;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.zeet.fin.dao.ServiceUserDao;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.service.CreateTransactionService;
import ru.zeet.fin.service.ServiceConfiguration;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args)  {

        ApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfiguration.class);

        CreateTransactionService service = context.getBean(CreateTransactionService.class);
        service.createTransaction(2L, 1L, BigDecimal.valueOf(20));

        //SecurityService securityService =  ServiceFactory.getSecurityService();
        //UserDto userDto = securityService.auth("user@mail.ru", "userpwd");
        //S/ystem.out.println(userDto);

       /* ServiceUserDao serviceUserDao = DaoFactory.getServiceUserDao();

        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setEmail("i@i.ru");
        serviceUser.setPassword("pwd");
        serviceUserDao.insert(serviceUser);

        System.out.println(serviceUser);*/


    }
}
