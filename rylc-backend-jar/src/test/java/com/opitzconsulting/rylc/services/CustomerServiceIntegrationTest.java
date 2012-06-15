package com.opitzconsulting.rylc.services;

import com.opitzconsulting.rylc.domain.Customer;
import com.opitzconsulting.rylc.domain.RylcUserDetails;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomerServiceIntegrationTest extends AbstractTransactionalIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void findCustomerWithUsername_noMatchingCustomer_returnsNull() {
        assertNull(customerService.findCustomerWithUsername("username"));
    }

    @Test
    public void findCustomerWithUsername_matchingCustomer_returnsCustomer() {
        RylcUserDetails user = getEntityPersister().createUser("username", "secret");
        Customer customer = user.getCustomer();
        assertEquals(customer, customerService.findCustomerWithUsername("username"));
    }

}
