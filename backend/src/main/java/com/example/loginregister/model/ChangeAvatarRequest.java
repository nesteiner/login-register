package com.example.loginregister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAvatarRequest {
    @NotNull(message = "avatarid cannot be null")
    Long avatarid;
}
