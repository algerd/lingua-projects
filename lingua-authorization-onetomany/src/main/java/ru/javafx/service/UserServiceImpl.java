package ru.javafx.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.entity.User;
import ru.javafx.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

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
    public User loadUserByUsername(String username) {           
        User user = userRepository.getByUsername(username);
        // make sure the authorities and password are lazy loaded
        user.getAuthorities().size();
        user.getPassword();
        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user, String newPassword) {
        if (newPassword != null && newPassword.length() > 0) {
            String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
            user.setHashedPassword(BCrypt.hashpw(newPassword, salt).getBytes());
        }
        userRepository.save(user);
    }
}
