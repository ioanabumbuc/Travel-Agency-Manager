package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.model.Cruise;

public interface CruiseRepo extends JpaRepository<Cruise, Long> {
}
