package com.example.loginregister.service;

import com.example.loginregister.model.ImageItem;
import com.example.loginregister.repository.ImageItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageItemService {
    private static final long DEFAULT_ID = 1;
    @Autowired
    ImageItemRepository imageItemRepository;

    public ImageItem insertOne(ImageItem data) {
        return imageItemRepository.save(data);
    }

    public Optional<ImageItem> findOne(String name) {
        return imageItemRepository.findByName(name);
    }

    public Optional<ImageItem> findOne(Long id) {
        return imageItemRepository.findById(id);
    }

    public void deleteOne(Long id) {
        imageItemRepository.deleteById(id);
    }
    public ImageItem findDefault() {
        return imageItemRepository.findById(DEFAULT_ID).get();
    }
}
