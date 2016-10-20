package ru.javafx.entity;

import javax.persistence.Column;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Embeddable
@Table(
    name="user_authority",    
    uniqueConstraints = {@UniqueConstraint(name = "uk_users_id_authority", columnNames = {"users_id", "authority"})
})
public class UserAuthority implements GrantedAuthority {

    private String authority;

    public UserAuthority() {
    }

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    @Column(name="authority")
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
