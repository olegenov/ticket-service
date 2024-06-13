package org.tickets.services.Impl;

import org.springframework.stereotype.Service;
import org.tickets.models.Order;
import org.tickets.repository.OrderRepository;
import org.tickets.services.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> all() {
        return orderRepository.findAll();
    }

    @Override
    public Order get(Long id) {
        if (!orderRepository.existsById(id)) {
            return null;
        }

        return orderRepository.getReferenceById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByStatus(int status) {
        return orderRepository.findByStatus(status);
    }
}
