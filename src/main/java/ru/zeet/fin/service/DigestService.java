package ru.zeet.fin.service;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestService {
    public String hash(String password) { return DigestUtils.md2Hex(password); }
}