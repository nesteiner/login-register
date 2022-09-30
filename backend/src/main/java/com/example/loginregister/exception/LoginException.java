package com.example.loginregister.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginException extends Exception {
    String message;
}
