
package ru.javafx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(
    name="authorities",    
    uniqueConstraints = {@UniqueConstraint(name = "uk_authority", columnNames = "authority")
})
public class Authority implements GrantedAuthority {
    
    private long id;
    private String authority;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
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
