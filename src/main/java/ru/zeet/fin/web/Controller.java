package ru.zeet.fin.web;

public interface Controller<REQUEST, RESPONSE> {
    RESPONSE execute (REQUEST request);

    Class<REQUEST> getRequestClass();
}
