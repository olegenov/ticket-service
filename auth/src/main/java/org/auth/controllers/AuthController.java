package org.auth.controllers;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.validation.Valid;
import org.auth.models.CustomUser;
import org.auth.models.Session;
import org.auth.models.dto.LoginDto;
import org.auth.models.dto.RegisterDto;
import org.auth.services.SessionService;
import org.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;


    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterDto registerDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return getErrorsRequest(result);
        }

        var encoder = new BCryptPasswordEncoder();

        CustomUser user = new CustomUser();
        user.setNickname(registerDto.getNickname());
        user.setEmail(registerDto.getEmail());
        user.setPassword(encoder.encode(registerDto.getPassword()));
        user.setCreated(new Timestamp(System.currentTimeMillis()));

        if (userService.getByEmail(registerDto.getEmail()) != null) {
           return ResponseEntity.badRequest().body("User with same email exists");
        }

        userService.save(user);

        return getTokenResponseEntity(user);
    }

    private ResponseEntity<Object> getTokenResponseEntity(CustomUser user) {
        Session session = createJwtToken(user);

        sessionService.save(session);

        var response = new HashMap<String, Object>();

        response.put("token", session.getToken());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginDto loginDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return getErrorsRequest(result);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }


        CustomUser user = userService.getByEmail(loginDto.getEmail());

        return getTokenResponseEntity(user);
    }

    private ResponseEntity<Object> getErrorsRequest(BindingResult result) {
        var errors = result.getAllErrors();
        var errorsMap = new HashMap<String, String>();

        for (org.springframework.validation.ObjectError objectError : errors) {
            var error = (FieldError) objectError;
            errorsMap.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorsMap);
    }

    private Session createJwtToken(CustomUser user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(24 * 3600);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(user.getEmail())
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes())
        );

        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims
        );

        Session session = new Session();
        session.setUser_id(user.getId());
        session.setToken(encoder.encode(params).getTokenValue());
        session.setExpires(Timestamp.from(expiresAt));

        return session;
    }
}
