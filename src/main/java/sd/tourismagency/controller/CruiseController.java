package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.model.Cruise;
import sd.tourismagency.model.Vacation;
import sd.tourismagency.service.CruiseService;

import java.util.List;

@RestController
@RequestMapping("/cruises")
public class CruiseController {
    @Autowired
    private CruiseService cruiseService;

    @PostMapping("/add")
    //@PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> addCruise(@RequestBody Cruise cruise) {
        Cruise newCruise = cruiseService.addCruise(cruise);
        return new ResponseEntity<>(newCruise, HttpStatus.CREATED);
    }

    @GetMapping("/all")

    public ResponseEntity<List<Cruise>> getAllCruises(){
        List<Cruise> cruises = cruiseService.getAllCruises();
        return new ResponseEntity<>(cruises, HttpStatus.OK);
    }

}
