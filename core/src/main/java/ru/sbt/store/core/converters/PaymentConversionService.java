package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.PaymentDto;
import ru.sbt.store.core.entities.Payment;

@Service
public interface PaymentConversionService extends DtoConversionService<PaymentDto, Payment, Long> {
}
