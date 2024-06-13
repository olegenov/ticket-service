package org.auth.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "sessions")
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "user_id", nullable = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Long user_id;

    @Setter
    @Column(name = "token", nullable = false)
    private String token;

    @Setter
    @Column(name = "expires", nullable = false)
    private Timestamp expires;
}
