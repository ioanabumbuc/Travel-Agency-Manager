package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.dto.ReservationDto;
import sd.tourismagency.dto.ResponseDto;
import sd.tourismagency.dto.VacationDto;
import sd.tourismagency.model.*;
import sd.tourismagency.observer.EmailObserver;
import sd.tourismagency.observer.LogObserver;
import sd.tourismagency.observer.ReservationSubject;
import sd.tourismagency.service.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VacationService vacationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllReservations(@RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody ReservationDto reservationDto,
                                            @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Vacation vacation = vacationService.getById(reservationDto.getVacationId());
        if (vacation == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.VACATION_NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        Client client = clientService.getById(reservationDto.getClientId());
        if (client == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.CLIENT_NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        Reservation reservation = new Reservation();
        reservation.setVacation(vacation);
        reservation.setClient(client);
        reservation.setNumberOfPeople(reservationDto.getNumberOfPeople());
        if (reservationDto.getPaymentStatus().equals(PaymentStatus.PAY_ON_SPOT.name())) {
            reservation.setPaymentStatus(PaymentStatus.PAY_ON_SPOT);
        } else {
            reservation.setPaymentStatus(PaymentStatus.PENDING);
        }

        Reservation newReservation = reservationService.addReservation(reservation);
        if (newReservation == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.RESERVATION_FAIL.getText());
            return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
        }

        //notify observers
        ReservationSubject reservationSubject = new ReservationSubject();
        reservationSubject.addObserver(new LogObserver());
        reservationSubject.addObserver(new EmailObserver());
        reservationSubject.createReservation(newReservation);

        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<?> payReservation(@PathVariable("id") Long id,
                                            @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Reservation reservation = reservationService.findById(id);
        if (reservation == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        reservation.setPaymentStatus(PaymentStatus.PAYED);

        Reservation newReservation = reservationService.updateReservation(reservation);
        if (newReservation == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(newReservation, HttpStatus.OK);
    }

    @PutMapping("/update/{reservationId}")
    public ResponseEntity<?> updateReservation(@RequestBody ReservationDto reservationDto,
                                               @PathVariable("reservationId") Long id,
                                               @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Vacation vacation = vacationService.getById(reservationDto.getVacationId());
        if (vacation == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.VACATION_NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        Client client = clientService.getById(reservationDto.getId());
        if (client == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.CLIENT_NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        Reservation reservation = new Reservation();
        reservation.setVacation(vacation);
        reservation.setClient(client);
        reservation.setNumberOfPeople(reservationDto.getNumberOfPeople());

        if (reservationDto.getPaymentStatus().equals("PAYED")) {
            reservation.setPaymentStatus(PaymentStatus.PAYED);
        } else if (reservationDto.getPaymentStatus().equals("PAY_ON_SPOT")) {
            reservation.setPaymentStatus(PaymentStatus.PAY_ON_SPOT);
        } else {
            reservation.setPaymentStatus(PaymentStatus.PENDING);
        }

        Reservation newReservation = reservationService.updateReservation(reservation);
        if (newReservation == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        //notify observers
        ReservationSubject reservationSubject = new ReservationSubject();
        reservationSubject.addObserver(new LogObserver());
        reservationSubject.addObserver(new EmailObserver());
        reservationSubject.createReservation(newReservation);

        return new ResponseEntity<>(newReservation, HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable("id") Long id,
                                               @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        if (reservationService.existsById(id)) {
            reservationService.cancelReservation(id);
            ResponseDto resp = new ResponseDto(ResponseMsg.DELETE_SUCCESS.getText());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }


}
