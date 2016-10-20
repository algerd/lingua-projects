package ru.javafx.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javafx.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    User getByUsername(String username);
    
}
