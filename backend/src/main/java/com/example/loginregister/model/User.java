package com.example.loginregister.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 20, nullable = false)
    String name;

    @Column(length = 64, nullable = false)
    String email;

    @Column(length = 128, nullable = false)
    @JsonIgnore
    String passwordHash;

    @OneToOne
    @JoinColumn(name = "avatarid")
    ImageItem avatar;
}
