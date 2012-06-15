package com.opitzconsulting.rylc.services;

import com.opitzconsulting.rylc.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;


public class RentalServiceIntegrationTest extends AbstractTransactionalIntegrationTest {

    @Autowired
    private RentalService rentalService;

    @Test
    public void findRentalHistoryForCustomer() {
        RylcUserDetails user = getEntityPersister().createUser("fred", "pass");
        Customer customer = user.getCustomer();
        List<Rental> rentals = getEntityPersister().createRentalsFor(customer);
        List<Rental> customerHistory = rentalService.findRentalHistoryForCustomer(customer.getId());
        assertNotNull(customerHistory);
        Assert.assertEquals(rentals, customerHistory);
    }

    @Test
    public void findRentalHistoryForUnkownCustomer() {
        List<Rental> customerHistory = rentalService.findRentalHistoryForCustomer(-1L);
        assertTrue(customerHistory.isEmpty());
    }

    @Test
    public void findCarTypes() {
        List<String> carTypes = rentalService.findCarTypes();
        assertEquals("MINI", carTypes.get(0));
        assertEquals("ECONOMY", carTypes.get(1));
        assertEquals("PREMIUM", carTypes.get(2));
    }

    @Test
    public void findCitiesForRentalSelection() {
        List<String> cityNames = new ArrayList<String>();
        cityNames.add("Hamburg");
        cityNames.add("Berlin");
        getEntityPersister().createCities(cityNames.get(0), cityNames.get(1));
        Set<City> expectedCities = new HashSet<City>(getEntityRepository().findAll(City.class));
        Set<City> actualCities = new HashSet<City>(rentalService.findCities());
        assertEquals(expectedCities, actualCities);
    }

    @Test
    public void findAvailableCars() {
        String cityName = "Gummersbach";
        Date startDate = new Date();
        Date endDate = new Date();
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        Car car = getEntityPersister().createAvailableCarFor(cityName, maxPrice);
        List<Car> availableCars = this.rentalService.findAvailableCars(car.getHomeLocation().getId(), startDate, endDate, maxPrice);
        assertTrue(availableCars.size() == 1);
        assertEquals(car, availableCars.get(0));
    }

    @Test
    public void rentCar() throws Exception {
        String customerEmail = "martin.mustermann@web.de";
        Customer customer = getEntityPersister().createCustomer(customerEmail);
        String city = "Hamburg";
        BigDecimal maxPrice = BigDecimal.valueOf(100.0);
        Car car = getEntityPersister().createAvailableCarFor(city, maxPrice);
        Date startDate = new Date();
        Date endDate = new Date();
        Rental rental = this.rentalService.rentCar(customer, car.getId(), startDate, endDate);
        assertNotNull(rental);
        assertEquals(customerEmail, rental.getCustomer().getEmail());
        assertEquals(city, rental.getCar().getHomeLocation().getName());
        assertEquals(startDate, rental.getHireStartDate());
        assertEquals(endDate, rental.getHireEndDate());
        assertTrue(rental.getCar().getPrice().doubleValue() <= maxPrice.doubleValue());
    }

}
