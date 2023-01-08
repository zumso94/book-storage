package com.muslim.bookstorage.security;

import com.muslim.bookstorage.dao.UserDAO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserService implements UserDetailsService {
    private final UserDAO userDAO;

    public SecurityUserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUserName(username)
                .map(SecurityUser::fromDbUser)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
