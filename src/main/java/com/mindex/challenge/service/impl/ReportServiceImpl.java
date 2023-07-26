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
    @Override
    public ReportingStructure getReport(String id) {
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        int numberOfReports = getDirectReports(id);

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(numberOfReports);

        return reportingStructure;
    }

    private int getDirectReports(String id) {

        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        if(employee.getDirectReports() == null || employee.getDirectReports().size() == 0) {
            return 0;
        }
        else{
            int sum = employee.getDirectReports().size();
            for (Employee report : employee.getDirectReports()) {
                sum += getDirectReports(report.getEmployeeId());
            }
            return sum;
        }
    }

}
