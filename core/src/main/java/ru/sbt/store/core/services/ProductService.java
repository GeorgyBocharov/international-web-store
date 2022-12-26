package ru.sbt.store.core.services;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.entities.Info;
import ru.sbt.store.core.entities.Parameter;
import ru.sbt.store.core.entities.Product;



@Service
public interface ProductService extends CommonCRUDService<Product, Long> {
    Product findProductByIdAndLanguage(Long productId, Long languageId);
    Info addInfo(Info info);
    Info updateInfo(Info info);
    void deleteInfo(Long infoId);
    Parameter addParameter(Parameter parameter);
    Parameter updateParameter(Parameter parameter);
    void deleteParameter(Long parameterId);

}
