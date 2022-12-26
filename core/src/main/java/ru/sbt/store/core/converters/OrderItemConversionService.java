package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.OrderItemDto;
import ru.sbt.store.core.entities.OrderItem;

@Service
public interface OrderItemConversionService extends DtoConversionService<OrderItemDto, OrderItem, Long> {
}
