package org.example.nuiifo0d.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.nuiifo0d.enums.Role;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(unique=true, nullable=false)
    private String email;


    private String location;

    @Column(unique = true)
    private String phone;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique=true)
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;
}
