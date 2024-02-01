package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.dto.ResponseDto;
import sd.tourismagency.model.Client;
import sd.tourismagency.service.ClientService;
import sd.tourismagency.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllClients(@RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        List<Client> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addClient(@RequestBody Client client,
                                       @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Client newClient = clientService.addClient(client);
        if (newClient == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.EMAIL_EXISTS.getText());
            return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @PutMapping("/update/{clientId}")
    public ResponseEntity<?> updateClient(@RequestBody Client client,
                                          @PathVariable("clientId") Long id,
                                          @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        client.setId(id);
        Client newClient = clientService.updateClient(client);
        if (newClient == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id,
                                          @RequestHeader("Email") String email) {
        if (!employeeService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_EMPLOYEE.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        if (clientService.existsById(id)) {
            clientService.deleteClient(id);
            ResponseDto resp = new ResponseDto(ResponseMsg.DELETE_SUCCESS.getText());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }


}
