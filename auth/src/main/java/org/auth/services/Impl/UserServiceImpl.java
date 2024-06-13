package org.auth.services.Impl;

import org.auth.models.CustomUser;
import org.auth.repository.UserRepository;
import org.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByEmail(username);

        if (user != null) {
            var result = User.withUsername(user.getNickname())
                    .password(user.getPassword())
                    .build();

            return result;
        }

        return null;
    }

    @Override
    public CustomUser getByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public CustomUser save(CustomUser user) {
        return userRepository.save(user);
    }
}
