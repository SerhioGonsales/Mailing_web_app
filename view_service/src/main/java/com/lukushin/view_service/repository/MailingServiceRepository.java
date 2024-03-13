package com.lukushin.view_service.repository;

public interface MailingServiceRepository {
    boolean startMailing();
    boolean sendTestMessage();
}
