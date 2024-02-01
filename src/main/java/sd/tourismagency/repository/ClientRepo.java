package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.model.Client;

public interface ClientRepo extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);
}
