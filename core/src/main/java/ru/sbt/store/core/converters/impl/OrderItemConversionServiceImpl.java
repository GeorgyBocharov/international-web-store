package ru.sbt.store.core.converters.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.OrderItemConversionService;
import ru.sbt.store.core.converters.ProductConversionService;
import ru.sbt.store.core.dto.OrderItemDto;
import ru.sbt.store.core.entities.Order;
import ru.sbt.store.core.entities.OrderItem;
import ru.sbt.store.core.entities.Product;

@Component
@RequiredArgsConstructor
public class OrderItemConversionServiceImpl implements OrderItemConversionService {

    private final ProductConversionService productConversionService;

    @Override
    public OrderItemDto convertToDto(OrderItem entity) {
        if (entity == null) {
            return null;
        }
        OrderItemDto orderItemDto = convertToDtoNotFetched(entity);
        orderItemDto.setProductDto(productConversionService.convertToDto(entity.getProduct()));
        return orderItemDto;
    }

    @Override
    public OrderItemDto convertToDtoNotFetched(OrderItem entity) {
        if (entity == null) {
            return null;
        }
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(entity.getId());
        orderItemDto.setOrderId(entity.getOrderId());
        orderItemDto.setProductId(entity.getProductId());
        orderItemDto.setQuantity(entity.getQuantity());
        return orderItemDto;
    }

    @Override
    public OrderItem convertFromDto(OrderItemDto dto) {
        if (dto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setQuantity(dto.getQuantity());

        Product product = new Product();
        product.setId(dto.getProductId());
        orderItem.setProduct(product);

        Order order = new Order();
        order.setId(dto.getOrderId());
        orderItem.setOrder(order);

        return orderItem;
    }
}
