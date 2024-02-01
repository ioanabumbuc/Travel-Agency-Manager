package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.model.Tour;

public interface TourRepo extends JpaRepository<Tour, Long> {
}
