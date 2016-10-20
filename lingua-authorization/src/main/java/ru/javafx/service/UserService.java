package ru.javafx.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ru.javafx.entity.User;

@Validated
public interface UserService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username);

    void saveUser(@NotNull @Valid User user, String newPassword);
    
}
