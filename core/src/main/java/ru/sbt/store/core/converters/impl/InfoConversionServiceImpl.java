package ru.sbt.store.core.converters.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.InfoConversionService;
import ru.sbt.store.core.converters.LanguageConversionService;
import ru.sbt.store.core.dto.InfoDto;
import ru.sbt.store.core.entities.Info;
import ru.sbt.store.core.entities.Language;
import ru.sbt.store.core.entities.Product;

@Component
@RequiredArgsConstructor
public class InfoConversionServiceImpl implements InfoConversionService {

    private final LanguageConversionService languageConversionService;

    @Override
    public InfoDto convertToDto(Info info) {
        if (info == null) {
            return null;
        }
        InfoDto infoDto = convertToDtoNotFetched(info);
        infoDto.setLanguage(languageConversionService.convertToDto(info.getLanguage()));
        return infoDto;
    }

    @Override
    public InfoDto convertToDtoNotFetched(Info info) {
        if (info == null) {
            return null;
        }
        InfoDto infoDto = new InfoDto();
        infoDto.setId(info.getId());
        infoDto.setTitle(info.getTitle());
        infoDto.setDescription(info.getDescription());
        infoDto.setLanguageId(info.getLanguageId());
        infoDto.setProductId(info.getProductId());
        return infoDto;
    }

    @Override
    public Info convertFromDto(InfoDto infoDto) {
        if (infoDto == null) {
            return null;
        }
        Info info = new Info();
        info.setId(infoDto.getId());
        info.setTitle(infoDto.getTitle());
        info.setDescription(infoDto.getDescription());

        Language language = new Language();
        language.setId(infoDto.getLanguageId());
        info.setLanguage(language);

        if (infoDto.getProductId() != null) {
            Product product = new Product();
            product.setId(infoDto.getProductId());
            info.setProduct(product);
        }

        return info;
    }
}
