package ru.zeet.fin.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.zeet.fin.converter.ConverterConfiguration;
import ru.zeet.fin.converter.ServiceUserToUserDtoConverter;
import ru.zeet.fin.dao.DaoConfiguration;

@ComponentScan
@Import({DaoConfiguration.class, ConverterConfiguration.class})
@Configuration
public class ServiceConfiguration {
}
