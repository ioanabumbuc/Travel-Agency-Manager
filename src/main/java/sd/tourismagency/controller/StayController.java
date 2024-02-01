package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.model.Stay;
import sd.tourismagency.service.StayService;

import java.util.List;

@RestController
@RequestMapping("/stays")
public class StayController {
    @Autowired
    private StayService stayService;

    @PostMapping("/add")
    //@PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Stay> addStay(@RequestBody Stay stay) {
        Stay newStay = stayService.addStay(stay);
        return new ResponseEntity<>(newStay, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    //@PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<Stay>> getAllStays(){
        List<Stay> stays = stayService.getAllStays();
        return new ResponseEntity<>(stays, HttpStatus.OK);
    }
}
