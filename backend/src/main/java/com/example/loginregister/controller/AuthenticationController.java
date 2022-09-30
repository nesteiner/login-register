package com.example.loginregister.controller;

import com.example.loginregister.exception.LoginException;
import com.example.loginregister.model.LoginRequest;
import com.example.loginregister.model.LoginResponse;
import com.example.loginregister.utils.JwtTokenUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@Validated
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createToken(@RequestBody @Valid LoginRequest request, BindingResult result) throws LoginException {
        authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    void authenticate(@NonNull String username, @NonNull String password) throws LoginException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException exception) {
            throw new LoginException("user diabled");
        } catch (BadCredentialsException exception) { // this is for catching UsernameNotfoundException
            throw new LoginException("in AuthenticationController: no such user or password error");
        }
    }

}
