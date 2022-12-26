package ru.sbt.store.compositor.api.client;

import ru.sbt.common.entities.CurrencyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "core")
@Service
public interface CoreClient {
    @GetMapping("/currencies/all")
    List<CurrencyDto> getAllCurrencies();
}
