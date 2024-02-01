package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.model.Stay;

public interface StayRepo extends JpaRepository<Stay, Long> {
}
