package ru.sbt.store.core.wrapping.services;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.*;


import java.util.List;
import java.util.Set;

@Service
public interface OrderDtoService {
    OrderDto createOrder(CreateOrderDto orderDto);
    void deleteOrder(Long id);
    OrderDto findOrderById(Long id);
    List<OrderDto> getOrdersByClientId(Long clientId);
    OrderItemDto addOrderItem(OrderItemDto orderItemDto);
    OrderItemDto updateOrderItem(OrderItemDto orderDtoItem);
    void deleteOrderItem(Long id);
    ShipmentDto addShipment(ShipmentDto shipmentDto);
    ShipmentDto updateShipment(ShipmentDto shipmentDto);
    void deleteShipment(Long id);
    PaymentDto addPayment(PaymentDto paymentDto);
    PaymentDto updatePayment(PaymentDto paymentDto);
    void deletePayment(Long id);
    PaymentDto findPaymentById(Long id);

    OrderItemDto addProductToOrder(OrderDto orderDto, ProductDto productDto, int quantity);
    ShipmentDto addShipmentToOrder(OrderDto orderDto, String address);
    PaymentDto addPaymentToOrder(OrderDto orderDto, String cardPan, CurrencyDto currencyDto);
    OrderDto createOrder(ClientDto clientDto, CurrencyDto currencyDto, Set<OrderItemDto> itemDtoSet);
}
