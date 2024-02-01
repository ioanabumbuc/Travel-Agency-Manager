package sd.tourismagency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.tourismagency.controller.ReservationController;
import sd.tourismagency.dto.ReservationDto;
import sd.tourismagency.model.Client;
import sd.tourismagency.model.Cruise;
import sd.tourismagency.model.Reservation;
import sd.tourismagency.model.Vacation;
import sd.tourismagency.service.ClientService;
import sd.tourismagency.service.EmployeeService;
import sd.tourismagency.service.ReservationService;
import sd.tourismagency.service.VacationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerTest {

    @Mock
    private VacationService vacationService;
    @Mock
    private ReservationService reservationService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private ClientService clientService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    public void testAddReservation() {
        // Given
        Vacation vacation = new Cruise();
        vacation.setId(1L);
        Client client = new Client();
        client.setEmail("test@test.com");
        client.setId(1L);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setVacationId(vacation.getId());
        reservationDto.setClientId(client.getId());
        reservationDto.setNumberOfPeople(2);
        reservationDto.setPaymentStatus("PENDING");

        when(vacationService.getById(vacation.getId())).thenReturn(vacation);
        when(clientService.getById(client.getId())).thenReturn(client);
        when(reservationService.addReservation(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(employeeService.isLoggedIn(anyString())).thenReturn(true);

        // When
        ResponseEntity<?> responseEntity = reservationController.addReservation(reservationDto, "test3@test.com");

        // Then
        verify(vacationService).getById(vacation.getId());
        verify(clientService).getById(client.getId());
        verify(reservationService).addReservation(any(Reservation.class));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

}
