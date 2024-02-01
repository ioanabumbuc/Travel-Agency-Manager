package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.model.Reservation;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
}
