package ru.sbt.store.core.services;

import org.springframework.stereotype.Service;
import ru.sbt.store.core.entities.Client;

@Service
public interface ClientService extends CommonCRUDService<Client, Long>{
    Client saveNewObject(String name, String phone, Integer region);
}
