package sd.tourismagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.tourismagency.dto.ResponseDto;
import sd.tourismagency.model.Admin;
import sd.tourismagency.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        Admin a = adminService.login(admin);
        if (a != null) {
            return new ResponseEntity<>(a, HttpStatus.OK);
        }
        ResponseDto resp = new ResponseDto(ResponseMsg.LOGOUT_FAIL.getText());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Admin admin) {
        if (adminService.logout(admin.getEmail())) {
            ResponseDto resp = new ResponseDto(ResponseMsg.LOGOUT.getText());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        ResponseDto resp = new ResponseDto(ResponseMsg.LOGOUT_FAIL.getText());
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin) {
        Admin newAdmin = adminService.addAdmin(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }
}
