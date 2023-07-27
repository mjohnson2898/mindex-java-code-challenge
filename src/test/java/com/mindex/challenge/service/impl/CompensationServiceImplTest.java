package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String employeeIdUrl;

    private String compensationUrl;

    private String compensationIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void createAndReadCompensation() {
        // Getting John Lennon's employee entity
        Employee validEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();

        Compensation compensation = new Compensation();
        compensation.setEmployee(validEmployee);
        compensation.setSalary(100000);
        compensation.setEffectiveDate(Date.from(Instant.now()));
        
        // Create Assertions
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, compensation, Compensation.class).getBody();
        assertNotNull(createdCompensation.getEmployee());

        assertCompensationEqual(compensation, createdCompensation);
        
        // Read Assertions
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, validEmployee.getEmployeeId()).getBody();

        assertEquals(validEmployee.getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
        assertCompensationEqual(compensation, readCompensation);
    }
    
    @Test
    public void testCreateCompensation_EmployeeDoesNotExist(){
        Employee invalidEmployee = new Employee();
        invalidEmployee.setEmployeeId(UUID.randomUUID().toString());

        Compensation compensation = new Compensation();
        compensation.setEmployee(invalidEmployee);
        compensation.setSalary(100000);
        compensation.setEffectiveDate(Date.from(Instant.now()));

        // Create Assertions
        try{
            restTemplate.postForEntity(compensationUrl, compensation, Compensation.class);
        } catch (RuntimeException e){
            assertEquals(RuntimeException.class, e.getClass());
        }
    }

    private void assertCompensationEqual(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
        assertEquals(expected.getEmployee().getLastName(), actual.getEmployee().getLastName());
        assertEquals(expected.getEmployee().getDepartment(), actual.getEmployee().getDepartment());
        assertEquals(expected.getEmployee().getPosition(), actual.getEmployee().getPosition());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

}
