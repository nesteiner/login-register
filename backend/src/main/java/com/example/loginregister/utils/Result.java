package com.example.loginregister.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    Status code;
    String message;
    T data;

    public static <T> Result<T> Ok(String message, T data) {
        return new Result<>(Status.Ok, message, data);
    }

    public static <T> Result<T> Err(String message) {
        return new Result<>(Status.Err, message, null);
    }
}
