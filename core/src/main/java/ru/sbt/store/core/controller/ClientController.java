package ru.sbt.store.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sbt.store.core.converters.ClientConversionService;
import ru.sbt.store.core.dto.ClientDto;
import ru.sbt.store.core.entities.Client;
import ru.sbt.store.core.services.ClientService;

import javax.validation.Valid;

@RequestMapping("/clients")
@RestController
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;
    private final ClientConversionService clientConversionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientDto getClient(@PathVariable("id") Long id) {
        Client client = clientService.findObjectById(id);
        return clientConversionService.convertToDto(client);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientDto createClient(@Valid @RequestBody ClientDto clientDto) {
        Client client = clientConversionService.convertFromDto(clientDto);
        Client savedClient = clientService.saveNewObject(client);
        return clientConversionService.convertToDto(savedClient);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientDto updateClient(@Valid @RequestBody ClientDto clientDto) {
        Client client = clientConversionService.convertFromDto(clientDto);
        Client updatedClient = clientService.updateObject(client);
        return clientConversionService.convertToDto(updatedClient);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteClient(@PathVariable("id") Long id) {
        clientService.deleteObjectById(id);
    }

}
