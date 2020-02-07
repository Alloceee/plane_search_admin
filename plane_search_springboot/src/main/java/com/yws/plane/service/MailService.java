package com.yws.plane.service;

public interface MailService {
    boolean send(String to,String subject,String object);
}
