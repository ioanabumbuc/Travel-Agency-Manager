package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.dto.VacationDto;
import sd.tourismagency.model.Vacation;

public interface VacationRepo extends JpaRepository<Vacation,Long> {
}
