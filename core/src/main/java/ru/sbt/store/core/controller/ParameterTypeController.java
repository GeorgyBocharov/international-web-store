package ru.sbt.store.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sbt.store.core.converters.ParameterTypeConversionService;
import ru.sbt.store.core.dto.ParameterTypeDto;
import ru.sbt.store.core.entities.ParameterType;
import ru.sbt.store.core.services.ParameterTypeService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/parameter_types")
@RestController
@RequiredArgsConstructor
@Validated
public class ParameterTypeController {
    
    private final ParameterTypeService parameterTypeService;
    private final ParameterTypeConversionService parameterTypeConversionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterTypeDto getParameterType(@PathVariable("id") Long id) {
        ParameterType parameterType = parameterTypeService.findObjectById(id);
        return parameterTypeConversionService.convertToDto(parameterType);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterTypeDto createParameterType(@Valid @RequestBody ParameterTypeDto parameterTypeDto) {
        ParameterType parameterType = parameterTypeConversionService.convertFromDto(parameterTypeDto);
        ParameterType savedParameterType = parameterTypeService.saveNewObject(parameterType);
        return parameterTypeConversionService.convertToDto(savedParameterType);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterTypeDto updateParameterType(@Valid @RequestBody ParameterTypeDto parameterTypeDto) {
        ParameterType parameterType = parameterTypeConversionService.convertFromDto(parameterTypeDto);
        ParameterType updatedParameterType = parameterTypeService.updateObject(parameterType);
        return parameterTypeConversionService.convertToDto(updatedParameterType);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterTypeDto> getAllParameterTypes() {
        return parameterTypeService.findAllObjects()
                .stream()
                .map(parameterTypeConversionService::convertToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    public void deleteParameterType(@PathVariable("id") Long id) {
        parameterTypeService.deleteObjectById(id);
    }
}
