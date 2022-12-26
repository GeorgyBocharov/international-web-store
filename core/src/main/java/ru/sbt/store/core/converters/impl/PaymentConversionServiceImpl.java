package ru.sbt.store.core.converters.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.CurrencyConversionService;
import ru.sbt.store.core.converters.PaymentConversionService;
import ru.sbt.store.core.dto.PaymentDto;
import ru.sbt.store.core.entities.Currency;
import ru.sbt.store.core.entities.Order;
import ru.sbt.store.core.entities.Payment;

@Component
@RequiredArgsConstructor
public class PaymentConversionServiceImpl implements PaymentConversionService {

    private final CurrencyConversionService currencyConversionService;

    @Override
    public PaymentDto convertToDto(Payment entity) {
        if (entity == null) {
            return null;
        }
        PaymentDto paymentDto = convertToDtoNotFetched(entity);
        paymentDto.setCurrencyDto(currencyConversionService.convertToDto(entity.getCurrency()));
        return paymentDto;
    }

    @Override
    public PaymentDto convertToDtoNotFetched(Payment entity) {
        if (entity == null) {
            return null;
        }
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(entity.getId());
        paymentDto.setCardPan(entity.getEncryptedCardPan());
        paymentDto.setValue(entity.getValue());
        paymentDto.setCurrencyId(entity.getCurrencyId());
        paymentDto.setOrderId(entity.getOrderId());
        return paymentDto;
    }

    @Override
    public Payment convertFromDto(PaymentDto dto) {
        if (dto == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setCardPan(dto.getCardPan());

        Currency currency = new Currency();
        currency.setId(dto.getCurrencyId());
        payment.setCurrency(currency);

        Order order = new Order();
        order.setId(dto.getOrderId());
        payment.setOrder(order);

        return payment;
    }
}
