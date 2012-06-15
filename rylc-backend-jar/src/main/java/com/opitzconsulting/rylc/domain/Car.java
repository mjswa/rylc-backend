package com.opitzconsulting.rylc.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;

    private String description;

    private BigDecimal price;

    @ManyToOne
    private City homeLocation;

    @Enumerated(EnumType.STRING)
    private CarType carType;

    protected Car() {
    }

    public Car(String manufacturer, String description, BigDecimal price, City homeLocation, CarType carType) {
        this.manufacturer = manufacturer;
        this.description = description;
        this.price = price;
        this.homeLocation = homeLocation;
        this.carType = carType;
    }

    public Long getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(ToStringStyle.MULTI_LINE_STYLE);
        builder.append("id", id);
        builder.append("manufacturer", manufacturer);
        builder.append("description", description);
        return builder.toString();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public City getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(City homeLocation) {
        this.homeLocation = homeLocation;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }
}
