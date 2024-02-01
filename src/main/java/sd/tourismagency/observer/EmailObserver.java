package sd.tourismagency.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sd.tourismagency.model.Reservation;

import java.util.Observable;
import java.util.Observer;

@Component
public class EmailObserver implements Observer {

//    @Autowired
//    private JavaMailSender javaMailSender;
//
    @Override
    public void update(Observable o, Object arg) {
        Reservation reservation = (Reservation) arg;
//        sendEmail(reservation);
        System.out.println("Reservation created. Send email to " + reservation.getClient().getEmail());
    }
//
//    public void sendEmail(Reservation reservation) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("ioana.bumbuc@gmail.com");
//        message.setTo("ioana.bumbuc@gmail.com");
//        message.setSubject("Reservation created");
//
//        String mailText = "Thank you for choosing our company!\n\nReservation details:\n" + reservation.toString();
//
//        message.setText(mailText);
//
//        javaMailSender.send(message);
//    }
}
