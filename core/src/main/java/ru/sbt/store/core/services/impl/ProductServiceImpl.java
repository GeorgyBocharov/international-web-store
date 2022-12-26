package ru.sbt.store.core.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.store.core.entities.*;
import ru.sbt.store.core.exceptions.EntityAlreadyExistsException;
import ru.sbt.store.core.exceptions.EntityNotExistsException;
import ru.sbt.store.core.exceptions.FailedUpdateException;
import ru.sbt.store.core.repositories.*;
import ru.sbt.store.core.services.ProductService;



import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final InfoRepository infoRepository;
    private final ParameterRepository parameterRepository;
    private final LanguageRepository languageRepository;
    private final ParameterTypeRepository parameterTypeRepository;

    @Override
    public Product saveNewObject(@NonNull Product product) {
        if (product.getId() != null) {
            throw new EntityAlreadyExistsException(Product.class.getName(), product.getId());
        }
        Set<Info> infos = product.getInfos();
        Set<Parameter> parameters = product.getParameters();
        List<Long> languageIds = infos.stream().map(Info::getLanguageId).collect(Collectors.toList());
        List<Long> parameterTypesIds = parameters.stream().map(Parameter::getParameterTypeId).collect(Collectors.toList());

        Map<Long, Language> languageMap = languageRepository.findAllById(languageIds)
                .stream()
                .collect(Collectors.toMap(Language::getId, l -> l));


        Map<Long, ParameterType> parameterTypeMap = parameterTypeRepository.findAllById(parameterTypesIds)
                .stream()
                .collect(Collectors.toMap(ParameterType::getId, p -> p));

        infos.forEach(info -> {
            info.setLanguage(languageMap.get(info.getLanguageId()));
            info.setProduct(product);
        });
        parameters.forEach(param -> {
            param.setParameterType(parameterTypeMap.get(param.getParameterTypeId()));
            param.setProduct(product);
        });

        log.debug("After changes applying = {}, params = {}", infos, parameters);

        return productRepository.save(product);
    }

    @Override
    public Product findObjectById(@NonNull Long id) {
        return productRepository.findProductById(id);
    }


    @Override
    public Product findProductByIdAndLanguage(Long productId, Long languageId) {
        return productRepository.findProductByIdAndLanguage(productId, languageId);
    }

    @Override
    public Product updateObject(@NonNull Product product) {
        if (product.getId() == null) {
            throw new FailedUpdateException(Product.class.getName());
        }
        Product storedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new EntityNotExistsException(product.getId(), Product.class.getName()));
        if (storedProduct != null) {
            storedProduct.setPriceCU(product.getPriceCU());
            productRepository.save(storedProduct);
        }
        return storedProduct;
    }

    @Override
    public void deleteObjectById(@NonNull Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllObjects() {
        Map<Long, Product> productMap = new HashMap<>();
        productRepository.findAll()
                .forEach(p -> productMap.put(p.getId(), p));
        for (Info info: infoRepository.findAllByProductIdIn(productMap.keySet())) {
            productMap.get(info.getProductId()).addInfo(info);
        }
        for (Parameter parameter: parameterRepository.findAllByProductIdIn(productMap.keySet())) {
            productMap.get(parameter.getProductId()).addParameter(parameter);
        }
        return new ArrayList<>(productMap.values());
    }

    @Override
    public Info addInfo(Info info) {
        if (info.getId() != null) {
            throw new EntityAlreadyExistsException(Info.class.getName(), info.getId());
        }
        Language language = languageRepository.findById(info.getLanguageId())
                .orElseThrow(() -> new EntityNotExistsException(info.getLanguageId(), Language.class.getName()));

        Product product = productRepository.findById(info.getProductId())
                .orElseThrow(() -> new EntityNotExistsException(info.getProductId(), Product.class.getName()));

        info.setProduct(product);
        info.setLanguage(language);

        Info savedInfo = infoRepository.save(info);
        savedInfo.setLanguage(language);

        return savedInfo;
    }

    @Override
    public Info updateInfo(Info info) {
        if (info.getId() == null) {
            throw new FailedUpdateException(Info.class.getName());
        }
        Info storedInfo = infoRepository.findById(info.getId())
                .orElseThrow(() -> new EntityNotExistsException(info.getId(), Info.class.getName()));
        if (storedInfo != null) {
            storedInfo.setTitle(info.getTitle());
            storedInfo.setDescription(info.getDescription());
            infoRepository.save(storedInfo);
        }
        return storedInfo;
    }

    @Override
    public void deleteInfo(Long infoId) {
        infoRepository.deleteById(infoId);
    }

    @Override
    public Parameter addParameter(Parameter parameter) {
        if (parameter.getId() != null) {
            throw new EntityAlreadyExistsException(Parameter.class.getName(), parameter.getId());
        }

        ParameterType parameterType = parameterTypeRepository.findById(parameter.getProductId())
                .orElseThrow(() -> new EntityNotExistsException(parameter.getParameterTypeId(), ParameterType.class.getName()));

        Product product = productRepository.findById(parameter.getProductId())
                .orElseThrow(() -> new EntityNotExistsException(parameter.getProductId(), Product.class.getName()));

        parameter.setProduct(product);
        parameter.setParameterType(parameterType);

        Parameter savedParameter = parameterRepository.save(parameter);
        savedParameter.setParameterType(parameterType);

        return savedParameter;
    }

    @Override
    public Parameter updateParameter(Parameter parameter) {
        if (parameter.getId() == null) {
            throw new FailedUpdateException(Parameter.class.getName());
        }
        Parameter storedParameter = parameterRepository.findById(parameter.getId())
                .orElseThrow(() -> new EntityNotExistsException(parameter.getId(), Parameter.class.getName()));
        if (storedParameter != null) {
            storedParameter.setValue(parameter.getValue());
            parameterRepository.save(storedParameter);
        }
        return storedParameter;
    }

    @Override
    public void deleteParameter(Long parameterId) {
        parameterRepository.deleteById(parameterId);
    }

}
