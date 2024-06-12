package org.auth.controllers;

import lombok.AllArgsConstructor;
import org.auth.models.CustomUser;
import org.auth.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;
    @GetMapping("/me")
    public ResponseEntity<CustomUser> getUser(Authentication auth) {
        var user = service.getByEmail(auth.getName());
        System.out.println(auth.getName());
        return ResponseEntity.ok(user);
    }
}
