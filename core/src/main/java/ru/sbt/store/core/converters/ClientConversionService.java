package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.ClientDto;
import ru.sbt.store.core.entities.Client;

@Service
public interface ClientConversionService extends DtoConversionService<ClientDto, Client, Long> {
}
