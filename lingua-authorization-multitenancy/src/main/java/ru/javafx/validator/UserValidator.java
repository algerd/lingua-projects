package ru.javafx.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javafx.entity.User;
import ru.javafx.service.UserService;

@Component
public class UserValidator implements Validator {
    
    @Autowired
    private UserService userService;
    
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;

        if (userService.isUserExist(user)) {
            errors.rejectValue("username", "error.user.username.duplicate");
        }
        
        /*
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.field.empty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "user.username.size");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "user.password.size");
        }
        */
    }
}
