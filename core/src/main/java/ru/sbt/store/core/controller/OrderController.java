package ru.sbt.store.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sbt.store.core.dto.*;
import ru.sbt.store.core.wrapping.services.OrderDtoService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderDtoService orderDtoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto createOrder(@Valid @RequestBody CreateOrderDto orderDto) {
        return orderDtoService.createOrder(orderDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto getOrderById(@PathVariable("id") Long id) {
        return orderDtoService.findOrderById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderDtoService.deleteOrder(id);
    }

    @GetMapping(value = "/per_client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDto> getOrdersByClientId(@PathVariable("id") Long id) {
        return orderDtoService.getOrdersByClientId(id);
    }

    @PostMapping(value = "/items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItemDto createOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {
        return orderDtoService.addOrderItem(orderItemDto);
    }

    @PutMapping(value = "/items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderItemDto updateOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {
        return orderDtoService.updateOrderItem(orderItemDto);
    }

    @DeleteMapping(value = "/items/{id}")
    public void deleteOrderItem(@PathVariable("id") Long id) {
        orderDtoService.deleteOrderItem(id);
    }

    @PostMapping(value = "/shipments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShipmentDto createShipment(@Valid @RequestBody ShipmentDto shipmentDto) {
        return orderDtoService.addShipment(shipmentDto);
    }

    @PutMapping(value = "/shipments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShipmentDto updateShipment(@Valid @RequestBody ShipmentDto shipmentDto) {
        return orderDtoService.updateShipment(shipmentDto);
    }

    @DeleteMapping(value = "/shipments/{id}")
    public void deleteShipment(@PathVariable("id") Long id) {
        orderDtoService.deleteShipment(id);
    }

    @PostMapping(value = "/payments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        return orderDtoService.addPayment(paymentDto);
    }

    @PutMapping(value = "/payments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto updatePayment(@Valid @RequestBody PaymentDto paymentDto) {
        return orderDtoService.updatePayment(paymentDto);
    }

    @GetMapping(value = "/payments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto getPaymentById(@PathVariable("id") Long id) {
        return orderDtoService.findPaymentById(id);
    }

    @DeleteMapping(value = "/payments/{id}")
    public void deletePayment(@PathVariable("id") Long id) {
        orderDtoService.deletePayment(id);
    }
}
