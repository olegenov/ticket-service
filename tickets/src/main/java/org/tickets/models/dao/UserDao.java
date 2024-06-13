package org.tickets.models.dao;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDao {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String email;
}
