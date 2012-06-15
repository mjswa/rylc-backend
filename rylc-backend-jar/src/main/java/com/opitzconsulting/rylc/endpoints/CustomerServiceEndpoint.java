package com.opitzconsulting.rylc.endpoints;

import com.opitzconsulting.rylc.domain.Customer;
import com.opitzconsulting.rylc.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static java.lang.String.format;

@Component
@Path("customers")
@Produces("application/json")
public class CustomerServiceEndpoint extends AbstractServiceEndpoint {

    @Autowired
    private CustomerService customerService;

    @GET
    public Response getCustomerWithUsername(@QueryParam("username") String username) {
        Customer customer = customerService.findCustomerWithUsername(username);
        if (customer == null)
            return notFound(username);
        return ok(customer);
    }

    private Response notFound(String username) {
        return createStatusResponse(Response.Status.NOT_FOUND, format("no customer with username '%s' found.", username));
    }

    private Response ok(Customer customer) {
        return createOkResponseFor(customer);
    }

}
