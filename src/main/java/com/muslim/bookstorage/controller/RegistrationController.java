package com.muslim.bookstorage.controller;

import com.muslim.bookstorage.dto.UserRegistrationDTO;
import com.muslim.bookstorage.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.registerUser(userRegistrationDTO);
    }

    @GetMapping("activation/{activationUuid}")
    public void activateUser(@PathVariable String activationUuid) {
        userService.activateUser(activationUuid);
    }
}
