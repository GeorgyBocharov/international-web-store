package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.OrderDto;
import ru.sbt.store.core.entities.Order;

@Service
public interface OrderConversionService extends DtoConversionService<OrderDto, Order, Long> {
}
