package org.auth.repository;

import org.auth.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByNickname(String nickname);

    CustomUser findByEmail(String email);
}
