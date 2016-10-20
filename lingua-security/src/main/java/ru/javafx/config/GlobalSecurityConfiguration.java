package ru.javafx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import ru.javafx.service.AuthenticationService;

@Configuration
@EnableGlobalAuthentication
public class GlobalSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void init(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationService);
    }

}