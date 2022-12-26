package ru.sbt.store.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sbt.store.core.dto.InfoDto;
import ru.sbt.store.core.dto.ParameterDto;
import ru.sbt.store.core.dto.ProductDto;
import ru.sbt.store.core.wrapping.services.ProductDtoService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/products")
@RestController
@Validated
public class ProductController {

    private ProductDtoService productDtoService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto getProduct(@PathVariable("id") Long id) {
        return productDtoService.findObjectById(id);
    }


    @GetMapping(value = "/{productId}/languages/{languageId}")
    public ProductDto getInfosLanguagesForProduct(@PathVariable("productId") Long productId,
                                                  @PathVariable("languageId") Long languageId) {
        return productDtoService.findObjectByIdAndLanguage(productId, languageId);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productDtoService.deleteObjectById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto saveProduct(@Valid @RequestBody ProductDto productDto) {
        return productDtoService.saveNewObject(productDto);
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto updateProductPrice(@Valid @RequestBody ProductDto productDto) {
        return productDtoService.updateObject(productDto);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> getAllProducts() {
        return productDtoService.findAllObjects();
    }

    @PostMapping(value = "/infos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoDto addInfo(@Valid @RequestBody InfoDto infoDto) {
        return productDtoService.addInfo(infoDto);
    }

    @PostMapping(value = "/parameters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterDto addParameter(@Valid @RequestBody ParameterDto parameterDto) {
        return productDtoService.addParameter(parameterDto);
    }


    @PutMapping(value = "/infos/update",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoDto updateInfo(@Valid @RequestBody InfoDto infoDto) {
        return productDtoService.updateInfo(infoDto);
    }

    @DeleteMapping(value = "/infos/{id}")
    public void deleteInfo(@PathVariable("id") Long id) {
        productDtoService.deleteInfo(id);
    }

    @PutMapping(value = "/params/update",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterDto updateParameter(@Valid @RequestBody ParameterDto parameterDto) {
        return productDtoService.updateParameter(parameterDto);
    }

    @DeleteMapping(value = "/params/{id}")
    public void deleteParameter(@PathVariable("id") Long id) {
        productDtoService.deleteParameter(id);
    }

    @Autowired
    public void setProductDtoService(ProductDtoService productDtoService) {
        this.productDtoService = productDtoService;
    }
}
