package com.muslim.bookstorage.dao;

import com.muslim.bookstorage.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByActivationUuid(String activationUuid);

    boolean existsByUserName(String userName);
}
