package com.example.loginregister;

import com.example.loginregister.model.ImageItem;
import com.example.loginregister.repository.ImageItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginRegisterApplicationTests {
    @Autowired
    ImageItemRepository imageItemRepository;

    @Test
    public void fakeImageItem() {
        imageItemRepository.deleteAll();
        ImageItem imageItem = new ImageItem(1L, "default.png", "/home/steiner/workspace/login-register/backend/upload/default.png");
        imageItemRepository.save(imageItem);
    }

}
