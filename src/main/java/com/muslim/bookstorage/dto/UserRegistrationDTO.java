package com.muslim.bookstorage.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDTO {
    @NotBlank
    @Size(min = 2, max = 30)
    private String userName;
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 30)
    private String password;
}
