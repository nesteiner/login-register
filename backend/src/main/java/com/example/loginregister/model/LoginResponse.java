package com.example.loginregister.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 2L;

    String jwttoken;
}
