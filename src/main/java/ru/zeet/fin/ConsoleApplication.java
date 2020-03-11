package ru.zeet.fin;

import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.dto.UserDto;
import ru.zeet.fin.exception.CommonServiceException;
import ru.zeet.fin.service.SecurityService;
import ru.zeet.fin.service.ServiceFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleApplication {

    private static SecurityService securityService;

    public static void main(String[] args) {
        securityService = ServiceFactory.getSecurityService();
        UserDto userDto = null;

        System.out.println("Выберите действие:");
        String action = requestString("(1) Авторизация пользователя\n(2) Регистрация пользователя\n");

        switch (action) {
            case "1": // Авторизация
                String email = requestString("Введите email:");
                String password = requestString("Введите пароль:");
                userDto = securityService.auth(email, password);

                System.out.println("userDetails = " + userDto);
                System.out.println("Вы авторизованы");
                break;

            case "2": // Регистрация
                email = requestString("Введите email:");
                password = requestString("Введите пароль:");
                userDto = securityService.register(email, password);
                if (userDto != null) {
                    System.out.println("Вы зарегистрированы");
                } else {
                    System.out.println("Такой пользователь уже существует");
                }
                break;
        }

        System.out.println("Welcome");
    }

    private static String requestString(String title) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(title);
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new CommonServiceException(e);
        }
    }

    private static int requestNumber(String title) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(title);
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            throw new CommonServiceException(e);
        }
    }
}
