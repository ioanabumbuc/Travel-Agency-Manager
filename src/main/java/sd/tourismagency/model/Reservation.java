package sd.tourismagency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sd.tourismagency.dto.ReservationDto;

import java.util.Observable;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Vacation vacation;
    @ManyToOne
    private Client client;
    @Column(nullable = false)
    private int numberOfPeople;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Reservation() {
    }

    public String toString() {
        return "Reservation " + getId() +
                "\nDestination " + getVacation().getDestination() +
                "\nClient " + getClient().toString() +
                "\nPayment Status " + getPaymentStatus().name();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
