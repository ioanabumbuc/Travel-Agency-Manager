package sd.tourismagency.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.tourismagency.model.Client;
import sd.tourismagency.repository.ClientRepo;

import java.util.List;

@Service
@Transactional
public class ClientService {
    @Autowired
    private final ClientRepo clientRepo;

    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    public Client getById(Long id){
        return clientRepo.findById(id).orElse(null);
    }

    public Client addClient(Client client) {
        if (clientRepo.existsByEmail(client.getEmail())) {
            return null;
        }
        return clientRepo.save(client);
    }

    public Client updateClient(Client client) {
        if (!clientRepo.existsById(client.getId())) {
            return null;
        }
        return clientRepo.save(client);
    }

    public void deleteClient(Long clientId) {
        clientRepo.deleteById(clientId);
    }

    public boolean existsById(Long id){
        return clientRepo.existsById(id);
    }

}
