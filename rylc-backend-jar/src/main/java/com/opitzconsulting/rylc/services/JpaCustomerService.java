package com.opitzconsulting.rylc.services;

import com.opitzconsulting.rylc.domain.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class JpaCustomerService implements CustomerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Customer findCustomerWithUsername(String username) {
        try {
            return (Customer) entityManager
                    .createQuery("select u.customer from RylcUserDetails u where u.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
