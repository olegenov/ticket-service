package org.auth.services;

import org.auth.models.CustomUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    CustomUser getByEmail(String email);

    CustomUser save(CustomUser customUser);
}
