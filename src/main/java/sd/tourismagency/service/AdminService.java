package sd.tourismagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sd.tourismagency.model.Admin;
import sd.tourismagency.repository.AdminRepo;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private final AdminRepo adminRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Admin> getAllAdmins(){
        return adminRepo.findAll();
    }

    public Admin addAdmin(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepo.save(admin);
    }

    public Admin login(Admin admin) {
        if(adminRepo.findAdminByEmail(admin.getEmail()).isEmpty()){
           return null;
        }
        Admin a = adminRepo.findAdminByEmail(admin.getEmail()).get();
        if(!passwordEncoder.matches(admin.getPassword(), a.getPassword())){
            return null;
        }
        if(a.isLoggedIn()){
            return a;
        }
        a.setLoggedIn(true);
        adminRepo.save(a);
        return a;
    }

    public boolean logout(String email){
        if(adminRepo.findAdminByEmail(email).isEmpty()){
            return false;
        }
        Admin a = adminRepo.findAdminByEmail(email).get();
        a.setLoggedIn(false);
        adminRepo.save(a);
        return true;
    }

    public boolean isLoggedIn(String email){
        if(adminRepo.findAdminByEmail(email).isEmpty()){
            return false;
        }
        Admin a = adminRepo.findAdminByEmail(email).get();
        return a.isLoggedIn();
    }


    public void deleteAdmin(Long id){
        adminRepo.deleteById(id);
    }
}
