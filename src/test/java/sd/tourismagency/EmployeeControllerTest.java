package sd.tourismagency;

import org.junit.Test;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import sd.tourismagency.controller.EmployeeController;
import sd.tourismagency.controller.ResponseMsg;
import sd.tourismagency.dto.ResponseDto;
import sd.tourismagency.model.Admin;
import sd.tourismagency.model.Employee;
import sd.tourismagency.service.AdminService;
import sd.tourismagency.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;
    @Mock
    private AdminService adminService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void testAddEmployee() {
        // Given
        Employee employee = Employee.builder()
                .email("john.doe@test.com")
                .fullName("John Doe")
                .password("TestPassword")
                .build();
        Employee newEmployee = Employee.builder()
                .email("john.doe@test.com")
                .fullName("John Doe")
                .password(passwordEncoder.encode("TestPassword"))
                .build();

        // When
        when(employeeService.addEmployee(employee)).thenReturn(newEmployee);
        when(adminService.isLoggedIn(anyString())).thenReturn(true);


        ResponseEntity<?> responseEntity = employeeController.addEmployee(employee, "admin");

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(newEmployee, responseEntity.getBody());


        // When
        when(employeeService.addEmployee(employee)).thenReturn(null);

        responseEntity = employeeController.addEmployee(employee, "admin");

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ResponseDto);
        assertEquals(ResponseMsg.EMAIL_EXISTS.getText(), ((ResponseDto) responseEntity.getBody()).getMessage());
    }


}
