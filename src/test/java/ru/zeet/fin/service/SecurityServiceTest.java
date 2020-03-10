package ru.zeet.fin.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.zeet.fin.converter.ServiceUserToUserDtoConverter;
import ru.zeet.fin.dao.ServiceUserDao;
import ru.zeet.fin.domain.ServiceUser;
import ru.zeet.fin.dto.UserDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceTest {
    @InjectMocks SecurityService subj;

    @Mock ServiceUserDao serviceUserDao;
    @Mock DigestService digestService;
    @Mock ServiceUserToUserDtoConverter converter;

    @Before
    public void setUp() throws Exception {
        //serviceUserDao = mock(ServiceUserDao.class);
        //digestService = mock(DigestService.class);
        //converter = mock(ServiceUserToUserDtoConverter.class);
        //subj = new SecurityService(serviceUserDao, digestService, converter);
    }

    @Test
    public void auth_userNotFoundByEmail() {
        when(serviceUserDao.findByEmail("user@mail.ru")).thenReturn(null);
        UserDto userDto = subj.auth("user@mail.ru", "userpwd");
        assertNull(userDto);
    }

    @Test
    public void auth_userFoundButPaswordWrong() {
        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setPassword("pass");

        when(serviceUserDao.findByEmail("user@mail.ru")).thenReturn(null);
        when(digestService.hash("pass")).thenReturn("some hash");

        UserDto userDto = subj.auth("user@mail.ru", "userpwd");
        assertNull(userDto);
    }

    @Test
    public void auth_ok() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setId(1L);
        serviceUser.setPassword("some pass");

        when(serviceUserDao.findByEmail("user@mail.ru")).thenReturn(serviceUser);
        when(digestService.hash("pass")).thenReturn("some pass");
        when(converter.convert(serviceUser)).thenReturn(userDto);

        UserDto userFromService = subj.auth("user@mail.ru", "pass");
        assertEquals(userDto, userFromService);
    }

}
