package org.tickets.models.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.tickets.models.dao.UserDao;

@Getter
@Setter
public class OrderRequest {
    @NotEmpty
    private Long to_station_id;

    @NotEmpty
    private Long from_station_id;

    private UserDao user;
}
