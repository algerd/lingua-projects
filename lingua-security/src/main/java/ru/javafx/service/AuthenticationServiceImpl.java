package ru.javafx.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.entity.User;
import ru.javafx.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final SecureRandom RANDOM;
    private static final int HASHING_ROUNDS = 10;

    static {
        try {
            RANDOM = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken credentials = (UsernamePasswordAuthenticationToken) authentication;
        String username = credentials.getPrincipal().toString();
        String password = credentials.getCredentials().toString();
        credentials.eraseCredentials();

        User user = userRepository.getByUsername(username);
        if (user == null) {
            logger.warn("Authentication failed for non-existent user {}.", username);
            return null;
        }
        if (!BCrypt.checkpw(password, new String(user.getPassword(), StandardCharsets.UTF_8))) {
            logger.warn("Authentication failed for user {}.", username);
            return null;
        }
        user.setAuthenticated(true);
        logger.debug("User {} successfully authenticated.", username);
        return user;
    }

    @Override
    public boolean supports(Class<?> c) {
        return c == UsernamePasswordAuthenticationToken.class;
    }

    @Override
    @Transactional
    public void saveUser(User user, String newPassword) {
        if (newPassword != null && newPassword.length() > 0) {
            String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
            user.setPassword(BCrypt.hashpw(newPassword, salt).getBytes());
        }
        userRepository.save(user);
    }
}
