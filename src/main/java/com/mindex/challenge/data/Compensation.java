package com.mindex.challenge.data;

import lombok.Data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private long salary;
    private Date effectiveDate;

    public Compensation (){

    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
