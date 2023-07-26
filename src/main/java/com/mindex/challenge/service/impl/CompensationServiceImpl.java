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
    @Override
    public Compensation create(Compensation compensation) {
        if(employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId()) == null){
            throw new RuntimeException("Employee ID does not exist in the db");
        }
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String id) {
        Compensation compensation = compensationRepository.findByEmployee_EmployeeId(id);
        if(compensation == null){
            throw new RuntimeException("Compensation does not exist");
        }
        return compensation;
    }
}
