package ru.sbt.store.core.wrapping.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.converters.*;
import ru.sbt.store.core.dto.*;
import ru.sbt.store.core.entities.*;
import ru.sbt.store.core.wrapping.services.OrderDtoService;



import ru.sbt.store.core.services.OrderService;
import ru.sbt.store.core.utils.OrderCostCalculationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderDtoServiceImpl implements OrderDtoService {

    private final ClientConversionService clientConversionService;
    private final CurrencyConversionService currencyConversionService;
    private final OrderItemConversionService orderItemConversionService;
    private final OrderConversionService orderConversionService;
    private final ProductConversionService productConversionService;
    private final ShipmentConversionService shipmentConversionService;
    private final PaymentConversionService paymentConversionService;

    private final OrderService orderService;

    @Override
    public OrderDto createOrder(ClientDto clientDto, CurrencyDto currencyDto, Set<OrderItemDto> itemDtoSet) {
        Client client = clientConversionService.convertFromDto(clientDto);
        Currency currency = currencyConversionService.convertFromDto(currencyDto);
        Set<OrderItem> items = itemDtoSet.stream().map(orderItemConversionService::convertFromDto).collect(Collectors.toSet());
        Order savedOrder = orderService.createOrder(client, currency, items);
        return orderConversionService.convertToDto(savedOrder);
    }

    @Override
    public OrderDto createOrder(CreateOrderDto orderDto) {
        Order order = orderConversionService.convertFromCreateOrderDto(orderDto);
        Order savedOrder = orderService.createOrder(order);
        return convertOrderToDto(savedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderService.deleteOrderById(id);
    }

    @Override
    public OrderDto findOrderById(Long id) {
        Order order = orderService.findOrderById(id);
        return convertOrderToDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByClientId(Long clientId) {
        List<Order> orders = orderService.getOrdersByClientId(clientId);
        List<OrderDto> orderDtoList = new ArrayList<>();
        if (orders != null) {
            orderDtoList = orders.stream()
                    .map(orderConversionService::convertToDtoNotFetched)
                    .collect(Collectors.toList());
        }
        return orderDtoList;
    }

    @Override
    public OrderItemDto addProductToOrder(OrderDto orderDto, ProductDto productDto, int quantity) {
        Order order = orderConversionService.convertFromDto(orderDto);
        Product product = productConversionService.convertFromDto(productDto);
        OrderItem orderItem = orderService.addProductToOrder(order, product, quantity);
        return orderItemConversionService.convertToDto(orderItem);
    }

    @Override
    public ShipmentDto addShipmentToOrder(OrderDto orderDto, String address) {
        Order order = orderConversionService.convertFromDto(orderDto);
        Shipment shipment = orderService.addShipmentToOrder(order, address);
        return shipmentConversionService.convertToDto(shipment);
    }

    @Override
    public PaymentDto addPaymentToOrder(OrderDto orderDto, String cardPan, CurrencyDto currencyDto) {
        Order order = orderConversionService.convertFromDto(orderDto);
        Currency currency = currencyConversionService.convertFromDto(currencyDto);
        Payment payment = orderService.addPaymentToOrder(order, cardPan, currency);
        return paymentConversionService.convertToDto(payment);
    }

    @Override
    public PaymentDto findPaymentById(Long id) {
        Payment payment = orderService.findPaymentById(id);
        return paymentConversionService.convertToDto(payment);
    }

    @Override
    public OrderItemDto addOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemConversionService.convertFromDto(orderItemDto);
        OrderItem savedOrderItem = orderService.addOrderItem(orderItem);
        return orderItemConversionService.convertToDto(savedOrderItem);
    }

    @Override
    public OrderItemDto updateOrderItem(OrderItemDto orderDtoItem) {
        OrderItem orderItem = orderItemConversionService.convertFromDto(orderDtoItem);
        OrderItem updatedOrderItem = orderService.updateOrderItem(orderItem);
        return orderItemConversionService.convertToDtoNotFetched(updatedOrderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderService.deleteOrderItem(id);
    }

    @Transactional
    @Override
    public ShipmentDto addShipment(ShipmentDto shipmentDto) {
        Shipment shipment = shipmentConversionService.convertFromDto(shipmentDto);
        Shipment savedShipment = orderService.addShipment(shipment);
        return shipmentConversionService.convertToDto(savedShipment);
    }

    @Override
    public ShipmentDto updateShipment(ShipmentDto shipmentDto) {
        Shipment shipment = shipmentConversionService.convertFromDto(shipmentDto);
        Shipment updatedShipment = orderService.updateShipment(shipment);
        return shipmentConversionService.convertToDto(updatedShipment);
    }

    @Override
    public void deleteShipment(Long id) {
        orderService.deleteShipment(id);
    }

    @Override
    public PaymentDto addPayment(PaymentDto paymentDto) {
        Payment payment = paymentConversionService.convertFromDto(paymentDto);
        Payment savedPayment = orderService.addPayment(payment);
        return paymentConversionService.convertToDto(savedPayment);
    }

    @Override
    public PaymentDto updatePayment(PaymentDto paymentDto) {
        Payment payment = paymentConversionService.convertFromDto(paymentDto);
        Payment updatedPayment = orderService.updatePayment(payment);
        return paymentConversionService.convertToDto(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        orderService.deletePayment(id);
    }

    private OrderItemDto convertOrderItemToDto(OrderItem item) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(item.getId());
        orderItemDto.setQuantity(item.getQuantity());
        orderItemDto.setProductDto(productConversionService.convertToDtoNotFetched(item.getProduct()));
        orderItemDto.setOrderId(item.getOrderId());
        return orderItemDto;
    }

    private OrderDto convertOrderToDto(Order order) {
        Set<OrderItemDto> items = order.getItems().stream()
                .map(this::convertOrderItemToDto)
                .collect(Collectors.toSet());

        Payment payment = order.getPayment();
        PaymentDto paymentDto = null;
        if (payment != null) {
            paymentDto = paymentConversionService.convertToDtoNotFetched(payment);
        }

        OrderDto orderDto = orderConversionService.convertToDtoNotFetched(order);
        orderDto.setCurrencyDto(currencyConversionService.convertToDto(order.getCurrency()));
        orderDto.setShipmentDto(shipmentConversionService.convertToDto(order.getShipment()));
        orderDto.setPaymentDto(paymentDto);
        orderDto.setCost(OrderCostCalculationService.calculateOrderCost(order, order.getCurrency()));
        orderDto.setOrderItemDtoSet(items);
        return orderDto;
    }

}
