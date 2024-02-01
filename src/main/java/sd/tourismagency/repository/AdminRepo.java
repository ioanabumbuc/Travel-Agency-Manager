package sd.tourismagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.tourismagency.model.Admin;
import sd.tourismagency.model.Employee;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    public Optional<Admin> findAdminByEmailAndPassword(String email, String password);

    public Optional<Admin> findAdminByEmail(String email);
}
