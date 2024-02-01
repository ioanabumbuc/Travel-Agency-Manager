package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.model.Tour;
import sd.tourismagency.service.TourService;

import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    @PostMapping("/add")
    //@PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Tour> addTour(@RequestBody Tour tour) {
        Tour newTour = tourService.addTour(tour);
        return new ResponseEntity<>(newTour, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    //@PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Tour>> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        return new ResponseEntity<>(tours, HttpStatus.OK);
    }

}
