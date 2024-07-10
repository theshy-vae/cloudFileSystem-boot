package com.hyj.cloud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("appConfig")
public class AppConfig {

    @Value("${spring.mail.username}")
    private String sendUsername;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${project.folder}")
    private String projectFolder;

    public String getSendUsername() {
        return sendUsername;
    }

//    public String getEmails() {
//        return emails;
//    }
    public String getProjectFolder() {
        return projectFolder;
    }
    public String getAdminUsername(){return adminUsername;}
}