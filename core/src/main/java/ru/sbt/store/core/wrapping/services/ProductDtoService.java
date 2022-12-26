package ru.sbt.store.core.wrapping.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.sbt.store.core.dto.InfoDto;
import ru.sbt.store.core.dto.ParameterDto;
import ru.sbt.store.core.dto.ProductDto;


import java.util.List;

@Service
public interface ProductDtoService {

    ProductDto saveNewObject(@NonNull ProductDto product);
    ProductDto findObjectById(@NonNull Long id);
    ProductDto findObjectByIdAndLanguage(@NonNull Long productId, @NonNull Long languageId);
    ProductDto updateObject(@NonNull ProductDto product);
    void deleteObjectById(@NonNull Long id);
    List<ProductDto> findAllObjects();
    InfoDto addInfo(InfoDto infoDto);
    InfoDto updateInfo(InfoDto infoDto);
    void deleteInfo(Long infoId);
    ParameterDto addParameter(ParameterDto parameterDto);
    ParameterDto updateParameter(ParameterDto parameter);
    void deleteParameter(Long parameterId);

}
