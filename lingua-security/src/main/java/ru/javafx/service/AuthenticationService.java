package ru.javafx.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ru.javafx.entity.User;

@Validated
public interface AuthenticationService extends AuthenticationProvider {

    @Override
    User authenticate(Authentication authentication);

    void saveUser(
        @NotNull @Valid User principal,
        String newPassword
    );
}
