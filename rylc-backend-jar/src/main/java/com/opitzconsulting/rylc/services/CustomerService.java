package com.opitzconsulting.rylc.services;

import com.opitzconsulting.rylc.domain.Customer;

public interface CustomerService {

    Customer findCustomerWithUsername(String username);

}
