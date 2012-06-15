package com.opitzconsulting.rylc.test;

import com.opitzconsulting.rylc.domain.*;

import java.math.BigDecimal;
import java.util.List;


public interface EntityPersister {

    Customer createCustomer(String email);
        
    RylcUserDetails createUser(String username, String password);

    List<Rental> createRentalsFor(Customer user);

    List<City> createCities(String... cityNames);

    Car createAvailableCarFor(String cityName, BigDecimal maxPrice);

}
