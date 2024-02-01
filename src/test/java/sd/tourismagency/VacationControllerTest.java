package sd.tourismagency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.tourismagency.controller.VacationController;
import sd.tourismagency.dto.VacationDto;
import sd.tourismagency.model.Attraction;
import sd.tourismagency.model.Cruise;
import sd.tourismagency.model.Stay;
import sd.tourismagency.model.Tour;
import sd.tourismagency.service.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VacationControllerTest {

    @Mock
    private AttractionService attractionService;

    @Mock
    private CruiseService cruiseService;

    @Mock
    private TourService tourService;

    @Mock
    private StayService stayService;
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private VacationController vacationController;

    @Test
    public void testAddVacationCruise() {
        // Given
        VacationDto vacationDto = new VacationDto();
        vacationDto.setDtype("CRUISE");
        vacationDto.setBoardingLocation("Boarding location");
        vacationDto.setShipName("Ship name");
        vacationDto.setAvailability(10);
        vacationDto.setDestination("Destination");
        vacationDto.setStartDate(LocalDate.now());
        vacationDto.setEndDate(LocalDate.now().plusDays(7));
        vacationDto.setPrice(1000.0);

        Cruise cruise = new Cruise();
        cruise.setBoardingLocation(vacationDto.getBoardingLocation());
        cruise.setShipName(vacationDto.getShipName());
        cruise.setAvailability(vacationDto.getAvailability());
        cruise.setDestination(vacationDto.getDestination());
        cruise.setStartDate(vacationDto.getStartDate());
        cruise.setEndDate(vacationDto.getEndDate());
        cruise.setPrice(vacationDto.getPrice());

        when(employeeService.isLoggedIn(anyString())).thenReturn(true);
        when(cruiseService.addCruise(cruise)).thenReturn(cruise);


        ResponseEntity<?> responseEntity = vacationController.addVacation(vacationDto, "test");

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(cruise, responseEntity.getBody());
        verify(cruiseService, times(1)).addCruise(cruise);
    }

    @Test
    public void testAddVacationTour() {
        // Given
        VacationDto vacationDto = new VacationDto();
        vacationDto.setDtype("TOUR");
        vacationDto.setGuide("Guide");
        vacationDto.setItinerary("Itinerary");
        vacationDto.setAvailability(10);
        vacationDto.setDestination("Destination");
        vacationDto.setStartDate(LocalDate.now());
        vacationDto.setEndDate(LocalDate.now().plusDays(7));
        vacationDto.setPrice(555.5);

        Tour tour = new Tour();
        tour.setGuide(vacationDto.getGuide());
        tour.setItinerary(vacationDto.getItinerary());
        tour.setAvailability(vacationDto.getAvailability());
        tour.setDestination(vacationDto.getDestination());
        tour.setStartDate(vacationDto.getStartDate());
        tour.setEndDate(vacationDto.getEndDate());
        tour.setPrice(vacationDto.getPrice());

        when(tourService.addTour(tour)).thenReturn(tour);
        when(employeeService.isLoggedIn(anyString())).thenReturn(true);

        // When
        ResponseEntity<?> responseEntity = vacationController.addVacation(vacationDto,"test3@test.com");

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(tour, responseEntity.getBody());
        verify(tourService, times(1)).addTour(tour);
    }

    @Test
    public void testAddVacationStay() {
        // Given
        Attraction attraction1 = new Attraction();
        attraction1.setName("attraction1");
        attraction1.setNeedsTicket(false);
        Attraction attraction2 = new Attraction();
        attraction2.setName("attraction 2");
        attraction2.setNeedsTicket(true);
        attraction2.setTicketPrice(15.99);
        attraction2.setSchedule("9am - 5pm, closed on Mondays");

        List<Long> attractionIds = Arrays.asList(1L, 2L);

        VacationDto vacationDto = new VacationDto();
        vacationDto.setDtype("STAY");
        vacationDto.setHotelName("Hotel name");
        vacationDto.setAttractions(attractionIds);
        vacationDto.setAvailability(13);
        vacationDto.setDestination("Destination");
        vacationDto.setStartDate(LocalDate.now());
        vacationDto.setEndDate(LocalDate.now().plusDays(7));
        vacationDto.setPrice(555.5);

        // Mocking attractionService.getAllByIds method to return attractions
        when(attractionService.getAllByIds(attractionIds)).thenReturn(Arrays.asList(attraction1, attraction2));
        when(employeeService.isLoggedIn(anyString())).thenReturn(true);

        Stay stay = new Stay();
        stay.setHotelName(vacationDto.getHotelName());
        stay.setAvailability(vacationDto.getAvailability());
        stay.setDestination(vacationDto.getDestination());
        stay.setStartDate(vacationDto.getStartDate());
        stay.setEndDate(vacationDto.getEndDate());
        stay.setPrice(vacationDto.getPrice());
        stay.setAttractions(Arrays.asList(attraction1, attraction2));

        // Mocking the AddStayCommand
        when(stayService.addStay(stay)).thenReturn(stay);

        // When
        ResponseEntity<?> response = vacationController.addVacation(vacationDto, "test3@test.com");

        // Then
        verify(attractionService, times(1)).getAllByIds(attractionIds);
        verify(stayService, times(1)).addStay(stay);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(stay, response.getBody());
    }
}
