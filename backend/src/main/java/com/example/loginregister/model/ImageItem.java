package com.example.loginregister.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "ImageItem")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 255, nullable = false)
    String name;

    @Column(length = 255, nullable = false)
    @JsonIgnore
    String path;
}
