package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.dto.ResponseDto;
import sd.tourismagency.model.Attraction;
import sd.tourismagency.service.AttractionService;
import sd.tourismagency.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/attractions")
public class AttractionController {
    @Autowired
    private AttractionService attractionService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAttractions(@RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        List<Attraction> attractions = attractionService.getAllAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAttraction(@RequestBody Attraction attraction,
                                           @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Attraction newAttraction = attractionService.addAttraction(attraction);
        return new ResponseEntity<>(newAttraction, HttpStatus.CREATED);
    }

    @PutMapping("/update/{attractionId}")
    public ResponseEntity<?> updateAttraction(@RequestBody Attraction attraction,
                                              @PathVariable("attractionId") Long id,
                                              @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        attraction.setId(id);
        Attraction newAttraction = attractionService.updateAttraction(attraction);
        if (newAttraction == null) {
            return new ResponseEntity<>(ResponseMsg.NOT_FOUND.getText(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newAttraction, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttraction(@PathVariable("id") Long id,
                                              @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        if (attractionService.existsById(id)) {
            attractionService.deleteAttraction(id);
            ResponseDto resp = new ResponseDto(ResponseMsg.DELETE_SUCCESS.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(ResponseMsg.NOT_FOUND.getText(), HttpStatus.NOT_FOUND);
    }

}
