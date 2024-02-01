package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.model.Employee;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    public Optional<Employee> findEmployeeByEmail(String email);

    public Optional<Employee> findEmployeeByEmailAndPassword(String email, String password);
}
