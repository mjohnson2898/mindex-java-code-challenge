package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.RestController;

public interface CompensationRepository extends MongoRepository<Compensation, String> {

    Compensation findByEmployee_EmployeeId(String id);


}
