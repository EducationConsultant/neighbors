package com.education.consultant.educon.service;

import com.education.consultant.educon.document.User;

public interface EmailService {
    public void sendEmail(User user, String subject);
}
