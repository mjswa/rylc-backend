package com.opitzconsulting.rylc.services;

import com.opitzconsulting.rylc.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class JpaRentalService implements RentalService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings({"unchecked"})
    @Transactional(readOnly = true)
    public List<City> findCities() {
        return entityManager.createQuery("select c from City c order by c.name").getResultList();
    }

    @Override
    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    @Transactional(readOnly = true)
    public List<Car> findAvailableCars(Long cityId, Date startDate, Date endDate, BigDecimal maxPrice) {
        Query query = entityManager.createQuery(
                "select c from Car c " +
                        "where not exists ( select r from Rental r where r.car.id = c.id and :startDate <= r.hireEndDate and :endDate >= r.hireStartDate )" +
                        "and c.homeLocation.id = :cityId " +
                        "and c.price <= :maxPrice " +
                        "order by c.manufacturer, c.description");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("cityId", cityId);
        query.setParameter("maxPrice", maxPrice);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findCarTypes() {
        List<String> carTypes = new ArrayList<String>();
        for (CarType carType : CarType.values()) {
            carTypes.add(carType.name());
        }
        return carTypes;
    }

    @Override
    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    @Transactional
    public Rental rentCar(Customer customer, Long carId, Date startDate, Date endDate) throws NotAvailableException {
        // check if car is still available
        Query query = entityManager.createQuery(
                "select c from Car c " +
                        "where not exists ( select r from Rental r where r.car.id = c.id and :startDate <= r.hireEndDate and :endDate >= r.hireStartDate )" +
                        "and c.id = :id");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("id", carId);
        List<Car> cars = query.getResultList();

        if (cars.size() == 0) {
            throw new NotAvailableException("Sorry, some other user has reserved the car just in this moment.");
        }
        Car car = cars.get(0);

        Rental rental = new Rental(startDate, endDate, car, customer);
        entityManager.persist(rental);
        entityManager.persist(car);
        return rental;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    @Transactional(readOnly = true)
    public List<Rental> findRentalHistoryForCustomer(Long customerId) {
        return entityManager
                .createQuery("select r from Rental r where r.customer.id = :customerId order by r.hireStartDate")
                .setParameter("customerId", customerId)
                .getResultList();
    }
}
