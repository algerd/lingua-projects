
package ru.javafx.lingua.client.resources;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Authority {
    
    private String authority;
    private final Set<User> users = new HashSet<>();

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.authority);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Authority other = (Authority) obj;
        return Objects.equals(this.authority, other.authority);
    }

    @Override
    public String toString() {
        return "Authority{authority=" + authority + '}';
    }
    
}
