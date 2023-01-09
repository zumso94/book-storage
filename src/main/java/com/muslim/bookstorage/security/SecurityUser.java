package com.muslim.bookstorage.security;

import com.muslim.bookstorage.entity.user.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SecurityUser extends User {

    private SecurityUser(String username,
                         String password,
                         boolean enabled,
                         boolean accountNonExpired,
                         boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public static UserDetails fromDbUser(com.muslim.bookstorage.entity.user.User user) {
        return new SecurityUser(
                user.getUserName(),
                user.getPassword(),
                user.getStatus().equals(UserStatus.ACTIVE),
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority(user.getUserRole().name())
                ));
    }
}
