package ru.sbt.store.core.services;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.entities.*;


import java.util.List;
import java.util.Set;


@Service
public interface OrderService {
    Order createOrder(Order order);
    void deleteOrderById(Long id);
    Order findOrderById(Long id);
    List<Order> getOrdersByClientId(Long id);

    OrderItem addOrderItem(OrderItem orderItem);
    OrderItem updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(Long id);

    Shipment addShipment(Shipment shipment);
    Shipment updateShipment(Shipment shipment);
    void deleteShipment(Long id);

    Payment addPayment(Payment payment);
    Payment findPaymentById(Long id);
    Payment updatePayment(Payment payment);
    void deletePayment(Long id);

    Order createOrder(Client client, Currency currency, Set<OrderItem> items);
    OrderItem addProductToOrder(Order order, Product product, int quantity);
    Shipment addShipmentToOrder(Order order, String address);
    Payment addPaymentToOrder(Order order, String cardPan, Currency currency);
}
