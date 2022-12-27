package ru.sbt.store.core.converters.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.*;

import ru.sbt.store.core.dto.CreateOrderDto;
import ru.sbt.store.core.dto.CreateOrderItemDto;
import ru.sbt.store.core.dto.OrderDto;
import ru.sbt.store.core.dto.OrderItemDto;
import ru.sbt.store.core.entities.*;
import ru.sbt.store.core.utils.OrderCostCalculationService;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConversionServiceImpl implements OrderConversionService {

    private final CurrencyConversionService currencyConversionService;
    private final PaymentConversionService paymentConversionService;
    private final ShipmentConversionService shipmentConversionService;
    private final OrderItemConversionService orderItemConversionService;

    @Override
    public OrderDto convertToDto(Order entity) {
        if (entity == null) {
            return null;
        }
        Set<OrderItemDto> itemDtoSet = entity.getItems()
                .stream()
                .map(orderItemConversionService::convertToDto)
                .collect(Collectors.toSet());

        OrderDto orderDto = convertToDtoNotFetched(entity);
        orderDto.setCost(OrderCostCalculationService.calculateOrderCost(entity, entity.getCurrency()));
        orderDto.setCurrencyDto(currencyConversionService.convertToDto(entity.getCurrency()));
        orderDto.setPaymentDto(paymentConversionService.convertToDto(entity.getPayment()));
        orderDto.setShipmentDto(shipmentConversionService.convertToDto(entity.getShipment()));
        orderDto.setClientId(entity.getClientId());
        orderDto.setOrderItemDtoSet(itemDtoSet);
        return orderDto;
    }

    @Override
    public OrderDto convertToDtoNotFetched(Order entity) {
        if (entity == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(entity.getId());
        orderDto.setClientId(entity.getClientId());
        orderDto.setCurrencyId(entity.getCurrencyId());
        return orderDto;
    }

    @Override
    public Order convertFromDto(OrderDto dto) {
        if (dto == null) {
            return null;
        }
        Order order = new Order();
        order.setId(dto.getId());

        Currency currency = new Currency();
        currency.setId(dto.getCurrencyId());
        order.setCurrency(currency);

        Client client = new Client();
        client.setId(dto.getClientId());
        order.setClient(client);

        order.setPayment(paymentConversionService.convertFromDto(dto.getPaymentDto()));
        order.setShipment(shipmentConversionService.convertFromDto(dto.getShipmentDto()));

        order.setItems(dto.getOrderItemDtoSet().stream().map(orderItemConversionService::convertFromDto).collect(Collectors.toSet()));
        return order;
    }

    @Override
    public Order convertFromCreateOrderDto(CreateOrderDto dto) {
        if (dto == null) {
            return null;
        }
        Order order = new Order();
        Client client = new Client();
        client.setId(dto.getClientId());
        order.setClient(client);
        Currency currency = new Currency();
        currency.setName(dto.getCurrencyName());
        order.setCurrency(currency);

        order.setPayment(paymentConversionService.convertFromDto(dto.getPayment()));
        order.setShipment(shipmentConversionService.convertFromDto(dto.getShipment()));

        dto.getItems().forEach(itemDto -> addItemDto(itemDto, order));
        return order;
    }

    private static void addItemDto(CreateOrderItemDto dto, Order order) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setQuantity(dto.getQuantity());
        Product product = new Product();
        product.setId(dto.getProductId());
        item.setProduct(product);
        order.addItem(item);
    }
}
