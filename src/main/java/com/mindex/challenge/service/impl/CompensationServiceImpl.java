package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompensationRepository compensationRepository;

    /***
     * Function to insert a new compensation into the Compensation Table
     *
     * @param compensation the body of the create request
     * @return the original compensation
     * @throws RuntimeException if the employee passed in does not exist in the Employee Table
     */
    @Override
    public Compensation create(Compensation compensation) {
        if(employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId()) == null){
            throw new RuntimeException("Employee ID does not exist in the db");
        }
        compensationRepository.insert(compensation);
        return compensation;
    }

    /***
     * Queries the database by the employeeId of the employee field of the compensation
     *
     * @param id the employeeId passed in through the request
     * @return the compensation that contains the id in the employee field
     * @throws RuntimeException if the query doesn't return a Compensation
     */
    @Override
    public Compensation read(String id) {
        Compensation compensation = compensationRepository.findByEmployee_EmployeeId(id);
        if(compensation == null){
            throw new RuntimeException("Compensation does not exist");
        }
        return compensation;
    }
}
