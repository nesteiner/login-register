package com.example.loginregister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "username cannot be blank")
    @Length(min = 5, message = "username length must greater than 5")
    String username;

    @NotBlank(message = "password cannot be blank")
    @Length(min = 8, message = "password length must greater than 8")
    String password;

}
