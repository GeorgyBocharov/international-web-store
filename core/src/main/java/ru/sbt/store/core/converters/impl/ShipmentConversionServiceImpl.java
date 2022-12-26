package ru.sbt.store.core.converters.impl;

import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.ShipmentConversionService;
import ru.sbt.store.core.dto.ShipmentDto;
import ru.sbt.store.core.entities.Order;
import ru.sbt.store.core.entities.Shipment;

@Component
public class ShipmentConversionServiceImpl implements ShipmentConversionService {

    @Override
    public ShipmentDto convertToDto(Shipment entity) {
        if (entity == null) {
            return null;
        }
        ShipmentDto shipmentDto = new ShipmentDto();
        shipmentDto.setId(entity.getId());
        shipmentDto.setAddress(entity.getAddress());
        shipmentDto.setOrderId(entity.getOrderId());
        return shipmentDto;
    }

    @Override
    public Shipment convertFromDto(ShipmentDto dto) {
        if (dto == null) {
            return null;
        }
        Shipment shipment = new Shipment();
        shipment.setId(dto.getId());
        shipment.setAddress(dto.getAddress());
        Order order = new Order();
        order.setId(dto.getOrderId());
        shipment.setOrder(order);
        return shipment;
    }
}
