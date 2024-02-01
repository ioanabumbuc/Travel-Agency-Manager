package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.dto.EmployeeDto;
import sd.tourismagency.dto.ResponseDto;
import sd.tourismagency.model.Employee;
import sd.tourismagency.service.AdminService;
import sd.tourismagency.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmployeeDto employee) {
        Employee e = employeeService.login(employee.getEmail(), employee.getPassword());
        if (e != null) {
            return new ResponseEntity<>(e, HttpStatus.OK);
        }
        ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FAIL.getText());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String email) {
        employeeService.logout(email);
        ResponseDto resp = new ResponseDto(ResponseMsg.LOGOUT.getText());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees(@RequestHeader("Email") String email) {
        if (!adminService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_ADMIN.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee,
                                         @RequestHeader("Email") String email) {
        if (!adminService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_ADMIN.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        Employee newEmployee = employeeService.addEmployee(employee);
        if (newEmployee == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.EMAIL_EXISTS.getText());
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee,
                                            @RequestHeader("Email") String email,
                                            @PathVariable("employeeId") Long id) {
        if (!adminService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_ADMIN.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        employee.setId(id);
        Employee newEmployee = employeeService.updateEmployee(employee);
        if (newEmployee == null) {
            ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id,
                                            @RequestHeader("Email") String email) {
        if (!adminService.isLoggedIn(email)) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGIN_FIRST_ADMIN.getText());
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
        if (employeeService.existsById(id)) {
            employeeService.deleteEmployee(id);
            ResponseDto resp = new ResponseDto(ResponseMsg.DELETE_SUCCESS.getText());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        ResponseDto resp = new ResponseDto(ResponseMsg.NOT_FOUND.getText());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }


}
