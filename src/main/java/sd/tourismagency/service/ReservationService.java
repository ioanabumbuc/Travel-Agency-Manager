package sd.tourismagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.tourismagency.model.Reservation;
import sd.tourismagency.model.Vacation;
import sd.tourismagency.repository.ReservationRepo;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private final ReservationRepo reservationRepo;
    @Autowired
    private final VacationService vacationService;

    public ReservationService(ReservationRepo reservationRepo, VacationService vacationService) {
        this.reservationRepo = reservationRepo;
        this.vacationService = vacationService;
    }

    public Reservation addReservation(Reservation reservation) {
        if (reservation.getNumberOfPeople() > reservation.getVacation().getAvailability()) {
            return null;
        }
        Vacation v = reservation.getVacation();
        v.setAvailability(v.getAvailability() - reservation.getNumberOfPeople());
        vacationService.updateVacation(v);
        return reservationRepo.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepo.findById(id).orElse(null);
    }

    public Reservation updateReservation(Reservation reservation) {
        if (!reservationRepo.existsById(reservation.getId())) {
            return null;
        }
        return reservationRepo.save(reservation);
    }

    public void cancelReservation(Long id) {
        reservationRepo.deleteById(id);
    }

    public Boolean existsById(Long id) {
        return reservationRepo.existsById(id);
    }
}
