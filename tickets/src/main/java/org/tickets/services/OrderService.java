package org.tickets.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.tickets.models.Order;

import java.util.List;

public interface OrderService {
    List<Order> all();

    Order get(Long id);

    Order save(Order dish);

    List<Order> findByStatus(int status);

    Order update(Order dish);
}
