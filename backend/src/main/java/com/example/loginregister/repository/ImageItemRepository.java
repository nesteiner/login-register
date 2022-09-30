package com.example.loginregister.repository;

import com.example.loginregister.model.ImageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageItemRepository extends JpaRepository<ImageItem, Long> {
    Optional<ImageItem> findByName(String name);
}
