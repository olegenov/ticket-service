package org.tickets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "from_station_id", nullable = false)
    @JsonIgnore
    private Station from_station_id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "to_station_id", nullable = false)
    @JsonIgnore
    private Station to_station_id;

    @Setter
    @Column(name = "created", nullable = false)
    private Timestamp created;

    @Setter
    @Column(name = "status", nullable = false)
    private Integer status;
}

