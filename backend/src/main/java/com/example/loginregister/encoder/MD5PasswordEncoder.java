package com.example.loginregister.encoder;

import com.example.loginregister.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MD5PasswordEncoder implements PasswordEncoder {
    @Autowired
    MD5Util md5Util;

    @Override
    public String encode(CharSequence rawPassword) {
        return md5Util.encode((String) rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
