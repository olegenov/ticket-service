package org.auth.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Setter
    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Column(name = "created", nullable = false)
    private Timestamp created;
}
