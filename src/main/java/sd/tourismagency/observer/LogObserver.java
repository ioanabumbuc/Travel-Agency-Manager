package sd.tourismagency.observer;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sd.tourismagency.model.Reservation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

@Service
public class LogObserver implements Observer {
    private final String fileName;

    public LogObserver() {
        this.fileName = "Reservations/Reservation_" + UUID.randomUUID().toString().substring(0,18) + ".txt";
    }

    @Override
    public void update(Observable o, Object arg) {
        Reservation reservation = (Reservation) arg;
        LocalDateTime now = LocalDateTime.now();

        // Write log message to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Reservation created at "+ now.toString() + "\n" + reservation.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
