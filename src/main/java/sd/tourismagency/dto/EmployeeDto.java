package sd.tourismagency.dto;

import jakarta.persistence.Column;

public class EmployeeDto {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
