package com.mindex.challenge.service.impl;

import com.mindex.challenge.controller.EmployeeController;
import com.mindex.challenge.controller.ReportController;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * A function to create a reporting structure for an employeeId passed in through the endpoint.
     * @param id the employeeId that the ReportingStructure is being created for
     * @return A ReportingStructure
     * @throws RuntimeException if the Employee does not exist in the Employee Repository
     */
    @Override
    public ReportingStructure getReport(String id) {
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        //initial call to the recursive function
        int numberOfReports = getDirectReports(id);

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(numberOfReports);

        return reportingStructure;
    }

    /**
     * A recursive function to go down the tree of direct reports of a direct report.
     * @param id the employeeId to look up
     * @return the sum of reports that single employee has
     * @throws RuntimeException if an employee does not exist in the Employee Repository
     */
    private int getDirectReports(String id) {

        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        // If direct reports is null or 0, there is no need to continue
        if(employee.getDirectReports() == null || employee.getDirectReports().size() == 0) {
            return 0;
        }
        else{
            // the initial size is the size of the list
            int sum = employee.getDirectReports().size();
            // recalls this function for each direct report and sees if they have any direct reports
            for (Employee report : employee.getDirectReports()) {
                sum += getDirectReports(report.getEmployeeId());
            }
            return sum;
        }
    }

}
