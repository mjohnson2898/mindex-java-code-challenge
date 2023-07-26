package com.mindex.challenge.data;

import lombok.Data;

import java.util.Date;

@Data
public class Compensation {
    private Employee employee;
    private long salary;
    private Date effectiveDate;

    public Compensation (){

    }
}
