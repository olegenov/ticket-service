package org.tickets.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class OrderReponse {
    private Long id;
    private Long user_id;
    private Long from_station_id;
    private Long to_station_id;
    private Timestamp created;
    private Integer status;
}
