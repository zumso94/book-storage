package com.muslim.bookstorage.service;

import com.muslim.bookstorage.dao.UserDAO;
import com.muslim.bookstorage.dto.UserRegistrationDTO;
import com.muslim.bookstorage.entity.user.User;
import com.muslim.bookstorage.entity.user.UserRole;
import com.muslim.bookstorage.entity.user.UserStatus;
import com.muslim.bookstorage.exception.UserNameAlreadyExistsException;
import com.muslim.bookstorage.exception.UserNotFoundException;
import com.muslim.bookstorage.mail.EmailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender mailSender;

    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder, EmailSender mailSender) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Transactional
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {

        if(userDAO.existsByUserName(userRegistrationDTO.getUserName())){
            throw new UserNameAlreadyExistsException("User name already exists");
        }

        String activationUuid = UUID.randomUUID().toString();
        mailSender.send(userRegistrationDTO.getEmail(),
                        "Account ACTIVATION", "http://192.168.8.100:8080/registration/activation/"+ activationUuid);

        User user = new User();
        user.setUserName(userRegistrationDTO.getUserName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setUserRole(UserRole.USER);
        user.setStatus(UserStatus.BANNED);
        user.setActivationUuid(activationUuid);

        userDAO.save(user);
    }

    @Transactional
    public void activateUser(String activationUuid){
        User user = userDAO.findByActivationUuid(activationUuid)
                .orElseThrow(()-> new UserNotFoundException("User with this Uuid Not Found"));
        user.setStatus(UserStatus.ACTIVE);
        user.setActivationUuid(null);
    }
}
