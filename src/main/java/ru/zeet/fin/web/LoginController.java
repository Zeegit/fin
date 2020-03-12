package ru.zeet.fin.web;

import ru.zeet.fin.web.request.LoginRequest;
import ru.zeet.fin.web.response.LoginResponse;

public class LoginController implements Controller<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse execute(LoginRequest request) {
        if ("zeet".equals(request.getUsername())) {
            return new LoginResponse(true);
        }
        return new LoginResponse(false);
    }

    @Override
    public Class<LoginRequest> getRequestClass() {
        return LoginRequest.class;
    }
}
