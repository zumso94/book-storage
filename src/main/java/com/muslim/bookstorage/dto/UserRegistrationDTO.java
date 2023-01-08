package com.muslim.bookstorage.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRegistrationDTO {
    @NotBlank
    @Size(min=2, max=30)
    private String userName;
    @Email
    private String email;
    @NotBlank
    @Size(min=4, max=30)
    private String password;
}
