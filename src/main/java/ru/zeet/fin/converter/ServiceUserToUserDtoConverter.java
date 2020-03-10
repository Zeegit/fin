package ru.zeet.fin.converter;

import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.dto.UserDto;

public class ServiceUserToUserDtoConverter implements Converter<ServiceUser, UserDto>{
    public UserDto convert(ServiceUser source) {
        UserDto target = new UserDto();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());

        return target;
    }
}
