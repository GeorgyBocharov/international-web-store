package ru.sbt.store.core.converters.impl;

import org.springframework.stereotype.Component;
import ru.sbt.store.core.converters.ClientConversionService;
import ru.sbt.store.core.dto.ClientDto;
import ru.sbt.store.core.entities.Client;

@Component
public class ClientConversionServiceImpl implements ClientConversionService {

    @Override
    public ClientDto convertToDto(Client client) {
        if (client == null) {
            return null;
        }
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setPhone(client.getEncryptedPhone());
        clientDto.setRegion(client.getRegion());
        return clientDto;
    }

    @Override
    public Client convertFromDto(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setPhone(clientDto.getPhone());
        client.setRegion(clientDto.getRegion());
        return client;
    }
}
