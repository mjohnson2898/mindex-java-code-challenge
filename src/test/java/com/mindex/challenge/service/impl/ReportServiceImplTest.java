package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;

    private String reportUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportService reportService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        reportUrl = "http://localhost:" + port + "/report/{id}";
    }


    @Test
    public void testGetReport() {
        // Testing DirectReport Counter
        // Getting John Lennon's employee info
        Employee expectedEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();

        ReportingStructure reportingStructure = restTemplate.getForEntity(reportUrl, ReportingStructure.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();

        assertEquals(4, reportingStructure.getNumberOfReports());

        assertEmployeeEquivalence(expectedEmployee, reportingStructure.getEmployee());

    }

    @Test
    public void testGetReport_invalidId() {
        try{
            restTemplate.getForEntity(reportUrl, ReportingStructure.class, UUID.randomUUID());
        } catch (RuntimeException e){
            assertTrue(e.getClass() == RuntimeException.class);
        }

    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
