package com.opitzconsulting.rylc.services;

import com.opitzconsulting.rylc.domain.Car;
import com.opitzconsulting.rylc.domain.City;
import com.opitzconsulting.rylc.domain.Customer;
import com.opitzconsulting.rylc.domain.Rental;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface RentalService {

    List<City> findCities();

    List<Car> findAvailableCars(Long cityId, Date startDate, Date endDate, BigDecimal maxPrice);

    List<String> findCarTypes();

    Rental rentCar(Customer customer, Long carId, Date startDate, Date endDate) throws NotAvailableException;

    List<Rental> findRentalHistoryForCustomer(Long customerId);
}
