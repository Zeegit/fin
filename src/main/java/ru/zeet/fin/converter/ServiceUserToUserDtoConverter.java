package ru.zeet.fin.converter;

import org.springframework.stereotype.Service;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.dto.UserDto;

@Service
public class ServiceUserToUserDtoConverter implements Converter<ServiceUser, UserDto>{
    public UserDto convert(ServiceUser source) {
        UserDto target = new UserDto();

        target.setId(source.getId());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());

        return target;
    }
}
