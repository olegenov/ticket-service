package org.tickets.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tickets.models.Order;
import org.tickets.services.OrderService;

import java.util.List;
import java.util.Random;

@Component
public class OrderProcessor {

    @Autowired
    private OrderService orderService;

    private final Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void processOrders() {
        List<Order> orders = orderService.findByStatus(1);

        for (Order order : orders) {
            try {
                Thread.sleep(random.nextInt(3000));

                int newStatus = random.nextBoolean() ? 2 : 3;
                order.setStatus(newStatus);

                orderService.save(order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }
    }
}

