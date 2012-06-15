package com.opitzconsulting.rylc.test;

import com.opitzconsulting.rylc.domain.Customer;
import com.opitzconsulting.rylc.domain.RylcUserDetails;

public class EntityFactory {

    private static final String UNKNOWN = "unknown";

    public static RylcUserDetails newUser(String username, String password) {
        return new RylcUserDetails(username, password, newCustomer(String.format("%s@%s", username, UNKNOWN)));
    }

    public static Customer newCustomer(String email) {
        return new Customer(UNKNOWN, email, UNKNOWN, UNKNOWN, UNKNOWN);
    }

}
