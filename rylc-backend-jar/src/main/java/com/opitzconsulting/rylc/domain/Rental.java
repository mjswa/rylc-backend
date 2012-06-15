package com.opitzconsulting.rylc.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Car car;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    private Date hireStartDate;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    private Date hireEndDate;

    @ManyToOne
    private Customer customer;

    protected Rental() {

    }

    public Rental(Date hireStartDate, Date hireEndDate, Car car, Customer customer) {
        this.hireStartDate = hireStartDate;
        this.hireEndDate = hireEndDate;
        this.car = car;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public Date getHireStartDate() {
        return hireStartDate;
    }

    public void setHireStartDate(Date hireStartDate) {
        this.hireStartDate = hireStartDate;
    }

    public Date getHireEndDate() {
        return hireEndDate;
    }

    public void setHireEndDate(Date hireEndDate) {
        this.hireEndDate = hireEndDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }
}
