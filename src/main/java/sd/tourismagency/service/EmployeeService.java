package sd.tourismagency.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sd.tourismagency.dto.EmployeeDto;
import sd.tourismagency.model.Admin;
import sd.tourismagency.model.Employee;
import sd.tourismagency.repository.EmployeeRepo;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private final EmployeeRepo employeeRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee login(String email, String password) {
        if (employeeRepo.findEmployeeByEmail(email).isEmpty()) {
            return null;
        }
        Employee e = employeeRepo.findEmployeeByEmail(email).get();
        if(!passwordEncoder.matches(password, e.getPassword())){
            return null;
        }
        if(e.isLoggedIn()){
            return e;
        }
        e.setLoggedIn(true);
        employeeRepo.save(e);
        return e;
    }

    public void logout(String email){
        if(employeeRepo.findEmployeeByEmail(email).isEmpty()){
            return;
        }
        Employee e = employeeRepo.findEmployeeByEmail(email).get();
        e.setLoggedIn(false);
        employeeRepo.save(e);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        if (employeeRepo.findById(employee.getId()).isEmpty()) {
            return null;
        }
        return employeeRepo.save(employee);
    }

    public Employee addEmployee(Employee employee) {
        if (employeeRepo.findEmployeeByEmail(employee.getEmail()).isPresent()) {
            return null;
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    public boolean existsById(Long id) {
        return employeeRepo.existsById(id);
    }

    public boolean isLoggedIn(String email){
        if(employeeRepo.findEmployeeByEmail(email).isEmpty()){
            return false;
        }
        Employee e = employeeRepo.findEmployeeByEmail(email).get();
        return e.isLoggedIn();
    }
}
