package ru.sbt.store.core.services.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.store.core.exceptions.EntityNotExistsException;
import ru.sbt.store.core.exceptions.FailedUpdateException;
import ru.sbt.store.core.exceptions.UniquePhoneException;
import ru.sbt.store.core.repositories.ClientRepository;
import ru.sbt.store.core.services.ClientService;
import ru.sbt.store.core.entities.Client;

import java.util.List;


@Component
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository repository;

    @Override
    public Client saveNewObject(String name, String phone, Integer region) {
        Client client = new Client();
        client.setName(name);
        client.setPhone(phone);
        client.setRegion(region);
        repository.save(client);
        return client;
    }

    @Override
    public Client saveNewObject(@NonNull Client client) {
        if (client.getId() != null) {
            log.error("Try to save existing client as new, use update method for such purposes");
            return client;
        }
        if (repository.findClientsNumberByPhone(client.getPhone()) > 0) {
            throw new UniquePhoneException(client.getEncryptedPhone());
        }
        return repository.save(client);
    }

    @Override
    public Client findObjectById(@NonNull Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Client updateObject(@NonNull Client client) {
        if (client.getId() == null) {
            throw new FailedUpdateException(Client.class.getName());
        }
        Client storedClient = repository.findById(client.getId())
                .orElseThrow(() -> new EntityNotExistsException(client.getId(), Client.class.getName()));
        if (!storedClient.getPhone().equals(client.getPhone())) {
            if (repository.findClientsNumberByPhone(client.getPhone()) > 0) {
                throw new UniquePhoneException(client.getEncryptedPhone());
            }
            storedClient.setPhone(client.getPhone());
        }
        storedClient.setName(client.getName());
        storedClient.setRegion(client.getRegion());
        return repository.save(storedClient);
    }

    @Override
    public void deleteObjectById(@NonNull Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Client> findAllObjects() {
        return repository.findAll();
    }

    @Autowired
    public void setRepository(ClientRepository repository) {
        this.repository = repository;
    }
}
