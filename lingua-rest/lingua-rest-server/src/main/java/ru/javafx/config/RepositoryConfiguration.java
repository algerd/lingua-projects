
package ru.javafx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import ru.javafx.entity.Authority;
import ru.javafx.entity.User;
import ru.javafx.entity.Word;

// For exposing ID in hal-response
@Configuration
public class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {
    
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(
            User.class,
            Authority.class,
            Word.class
        );
    }

}
