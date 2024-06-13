package org.tickets.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tickets.models.Order;
import org.tickets.models.Station;
import org.tickets.models.dao.UserDao;
import org.tickets.models.dto.OrderReponse;
import org.tickets.models.dto.OrderRequest;
import org.tickets.repository.OrderRepository;
import org.tickets.repository.StationRepository;
import org.tickets.services.Impl.OrderServiceImpl;
import org.tickets.services.Impl.StationServiceImpl;
import org.tickets.services.OrderService;
import org.tickets.services.StationService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final StationService stationService;

    @Autowired
    public OrderController(OrderRepository dishRepository, StationRepository stationRepository) {
        this.orderService = new OrderServiceImpl(dishRepository);
        this.stationService = new StationServiceImpl(stationRepository);
    }

    @GetMapping
    public ResponseEntity<List<OrderReponse>> getAllOrders() {
        List<Order> orders = orderService.all();

        List<OrderReponse> orderDTOs = orders.stream().map(this::getOrderResponse).collect(Collectors.toList());

        return ResponseEntity.ok(orderDTOs);
    }

    @PostMapping
    public ResponseEntity<OrderReponse> createOrder(@RequestBody OrderRequest request, @RequestAttribute("user") UserDao user) {
        Station from = stationService.get(request.getFrom_station_id());
        Station to = stationService.get(request.getTo_station_id());

        if (from == null || to == null) {
            return ResponseEntity.notFound().build();
        }

        Order order = new Order();

        order.setTo_station_id(to);
        order.setFrom_station_id(from);
        order.setUser_id(user.getId());
        order.setCreated(Timestamp.from(Instant.now()));
        order.setStatus(1);
        Order savedOrder = orderService.save(order);

        return ResponseEntity.ok(getOrderResponse(savedOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReponse> getOrder(@PathVariable Long id) {
        Order order = orderService.get(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(getOrderResponse(order));
    }

    private OrderReponse getOrderResponse(Order order) {
        OrderReponse response = new OrderReponse();

        response.setId(order.getId());
        response.setUser_id(order.getUser_id());
        response.setFrom_station_id(order.getFrom_station_id().getId());
        response.setTo_station_id(order.getTo_station_id().getId());
        response.setCreated(order.getCreated());
        response.setStatus(order.getStatus());

        return response;
    }
}

