package com.example.DifferentWorlds.Configuration;

import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Service.AuthenticationService;
import com.example.DifferentWorlds.dto.SignupRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    private final AdministratorRepository administratorRepository;
    private final AuthenticationService authenticationService;
    private final String name;
    private final String userName;
    private final String email;
    private final String password;

    public AdminInitializer(AdministratorRepository administratorRepository,
                            AuthenticationService authenticationService,
                            @Value("${app.admin.name}") String name,
                            @Value("${app.admin.username}") String userName,
                            @Value("${app.admin.email}") String email,
                            @Value("${app.admin.password}") String password) {
        this.administratorRepository = administratorRepository;
        this.authenticationService = authenticationService;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (administratorRepository.count() > 0) {
            return;
        }
        authenticationService.signupAdmin(new SignupRequestDTO(name, userName, email, password, null, null));
        logger.info("No admin found, created the initial admin '{}'", userName);
    }
}