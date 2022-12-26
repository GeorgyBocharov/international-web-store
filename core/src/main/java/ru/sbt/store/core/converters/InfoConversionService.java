package ru.sbt.store.core.converters;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.InfoDto;
import ru.sbt.store.core.entities.Info;

@Service
public interface InfoConversionService extends DtoConversionService<InfoDto, Info, Long> {
}
