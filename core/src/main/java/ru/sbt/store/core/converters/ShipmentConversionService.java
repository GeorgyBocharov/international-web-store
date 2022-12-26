package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.ShipmentDto;
import ru.sbt.store.core.entities.Shipment;

@Service
public interface ShipmentConversionService extends DtoConversionService<ShipmentDto, Shipment, Long> {
}
