package sd.tourismagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sd.tourismagency.repository.AdminRepo;
import sd.tourismagency.repository.EmployeeRepo;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin")) {
            return adminRepo.findAdminByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
        }
        return employeeRepo.findEmployeeByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
