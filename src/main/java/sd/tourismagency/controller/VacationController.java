package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.command.*;
import sd.tourismagency.dto.ResponseDto;
import sd.tourismagency.dto.VacationDto;
import sd.tourismagency.model.*;
import sd.tourismagency.service.*;

import java.util.List;

@RestController
@RequestMapping("/vacations")
public class VacationController {
    @Autowired
    private VacationService vacationService;
    @Autowired
    private StayService stayService;
    @Autowired
    private TourService tourService;
    @Autowired
    private CruiseService cruiseService;
    @Autowired
    private AttractionService attractionService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllVacations(@RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        List<Vacation> vacations = vacationService.getAllVacations();
        return new ResponseEntity<>(vacations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVacationById(@RequestHeader("Email") String email,
                                             @PathVariable("id") Long id){
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Vacation v = vacationService.getById(id);
        if(v == null){
            ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(v, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVacation(@RequestBody VacationDto vacationDto,
                                         @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Vacation vacation;
        if (vacationDto.getDtype().equals("CRUISE")) {
            vacation = new Cruise();
            ((Cruise) vacation).setBoardingLocation(vacationDto.getBoardingLocation());
            ((Cruise) vacation).setShipName(vacationDto.getShipName());
        } else if (vacationDto.getDtype().equals("TOUR")) {
            vacation = new Tour();
            ((Tour) vacation).setGuide(vacationDto.getGuide());
            ((Tour) vacation).setItinerary(vacationDto.getItinerary());
        } else {
            vacation = new Stay();
            ((Stay) vacation).setHotelName(vacationDto.getHotelName());
            ((Stay) vacation).setAttractions(attractionService.getAllByIds(vacationDto.getAttractions()));
        }
        vacation.setAvailability(vacationDto.getAvailability());
        vacation.setDestination(vacationDto.getDestination());
        vacation.setStartDate(vacationDto.getStartDate());
        vacation.setEndDate(vacationDto.getEndDate());
        vacation.setPrice(vacationDto.getPrice());

        Command addVacationCommand;
        if (vacation instanceof Cruise cruise) {
            addVacationCommand = new AddCruiseCommand(cruise, cruiseService);
        } else if (vacation instanceof Tour tour) {
            addVacationCommand = new AddTourCommand(tour, tourService);
        } else {
            addVacationCommand = new AddStayCommand((Stay) vacation, stayService);
        }

        Object newVacation = addVacationCommand.execute();

        return new ResponseEntity<>(newVacation, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateVacation(@RequestHeader("Email") String email,
                                            @PathVariable("id") Long id,
                                            @RequestBody VacationDto vacationDto) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Vacation vacation;
        if (vacationDto.getDtype().equals("CRUISE")) {
            vacation = new Cruise();
            ((Cruise) vacation).setBoardingLocation(vacationDto.getBoardingLocation());
            ((Cruise) vacation).setShipName(vacationDto.getShipName());
        } else if (vacationDto.getDtype().equals("TOUR")) {
            vacation = new Tour();
            ((Tour) vacation).setGuide(vacationDto.getGuide());
            ((Tour) vacation).setItinerary(vacationDto.getItinerary());
        } else {
            vacation = new Stay();
            ((Stay) vacation).setHotelName(vacationDto.getHotelName());
            ((Stay) vacation).setAttractions(attractionService.getAllByIds(vacationDto.getAttractions()));
        }
        vacation.setAvailability(vacationDto.getAvailability());
        vacation.setDestination(vacationDto.getDestination());
        vacation.setStartDate(vacationDto.getStartDate());
        vacation.setEndDate(vacationDto.getEndDate());
        vacation.setPrice(vacationDto.getPrice());

        Vacation newVacation = vacationService.updateVacation(vacation);
        return new ResponseEntity<>(newVacation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVacation(@RequestHeader("Email") String email,
                                            @PathVariable("id") Long id) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        this.vacationService.deleteVacation(id);
        ResponseDto resp = new ResponseDto(ResponseMsg.DELETE_SUCCESS.getText());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
