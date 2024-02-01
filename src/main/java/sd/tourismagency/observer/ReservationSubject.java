package sd.tourismagency.observer;

import sd.tourismagency.model.Reservation;

import java.util.Observable;
import java.util.Observer;

public class ReservationSubject extends Observable {
    public void createReservation(Reservation reservation) {
        setChanged();
        notifyObservers(reservation);
    }
}
