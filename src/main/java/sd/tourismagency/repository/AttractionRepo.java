package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.model.Attraction;
import sd.tourismagency.model.Stay;

import java.util.List;

public interface AttractionRepo extends JpaRepository<Attraction,Long> {
}
