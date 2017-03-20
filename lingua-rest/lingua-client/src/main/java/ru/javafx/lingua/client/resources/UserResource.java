
package ru.javafx.lingua.client.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class UserResource extends Resource<User> {

    public UserResource(User content, Link... links) {
        super(content, links);
    }

}
