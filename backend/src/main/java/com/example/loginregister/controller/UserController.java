package com.example.loginregister.controller;

import com.example.loginregister.model.ChangeAvatarRequest;
import com.example.loginregister.model.ImageItem;
import com.example.loginregister.model.User;
import com.example.loginregister.model.RegisterRequest;
import com.example.loginregister.service.ImageItemService;
import com.example.loginregister.service.UserService;
import com.example.loginregister.utils.JwtTokenUtil;
import com.example.loginregister.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ImageItemService imageItemService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public Result<User> insertOne(@RequestBody @Valid RegisterRequest request, BindingResult result) {
        return Result.Ok("insert ok", userService.insertOne(request));
    }

    @GetMapping("/user")
    public Result<User> findOne(HttpServletRequest request) throws UsernameNotFoundException {
        String name = jwtTokenUtil.getUsernameFromRequest(request);
        Optional<User> optionalUser = userService.findOne(name);
        return optionalUser.map(user -> Result.Ok("user", user)).orElseThrow(() -> new UsernameNotFoundException("no such user"));
    }

    @PutMapping("/user/avatar")
    public Result<User> updateOne(@RequestBody @Valid ChangeAvatarRequest changeAvatarRequest, BindingResult result, HttpServletRequest request) throws UsernameNotFoundException {
        String username = jwtTokenUtil.getUsernameFromRequest(request);
        Optional<User> optionalUser = userService.findOne(username);
        return optionalUser.map(user -> {

            Optional<ImageItem> optionalAvatar = imageItemService.findOne(changeAvatarRequest.getAvatarid());
            optionalAvatar.ifPresent(avatar -> {
                user.setAvatar(avatar);
                userService.updateOne(user);
            });


            return Result.Ok("update avatar ok", user);
        }).orElseThrow(() -> new UsernameNotFoundException("no such user"));
    }
}
