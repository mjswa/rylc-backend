package com.opitzconsulting.rylc.endpoints;


import com.opitzconsulting.rylc.domain.Car;
import com.opitzconsulting.rylc.domain.Rental;
import com.opitzconsulting.rylc.services.NotAvailableException;
import com.opitzconsulting.rylc.services.RentalService;
import com.opitzconsulting.rylc.util.AuthenticationService;
import com.opitzconsulting.rylc.util.DateUtil;
import flexjson.JSONDeserializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@Produces("application/json")
public class RentalServiceEndpoint extends AbstractServiceEndpoint {

    private static final Log LOG = LogFactory.getLog(RentalServiceEndpoint.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RentalService rentalService;

    @GET
    @Path("cities")
    public Response findCities() {
        return createOkResponseFor(this.rentalService.findCities());
    }

    @GET
    @Path("availableCars")
    public Response findAvailableCars(@QueryParam("cityId") long cityId,
                                      @QueryParam("startDate") String startDate,
                                      @QueryParam("endDate") String endDate,
                                      @QueryParam("maxPrice") BigDecimal maxPrice) {
        List<Car> cars = this.rentalService.findAvailableCars(cityId, DateUtil.toDate(startDate), DateUtil.toDate(endDate), maxPrice);
        return createOkResponseFor(cars);
    }

    @GET
    @Path("cartypes")
    public Response findCarTypes() {
        List<String> carTypes = this.rentalService.findCarTypes();
        return createOkResponseFor(carTypes);
    }

    @POST
    @Path("rental")
    @SuppressWarnings({"unchecked"})
    public Response rentCar(@RequestBody String requestBody) {
        LOG.debug("requestBody=" + requestBody);
        JSONDeserializer<Map> jsonDeserializer = new JSONDeserializer();
        Map<String, Object> parameter = jsonDeserializer.deserialize(requestBody);
        Rental rental;
        try {
            rental = this.rentalService.rentCar(authenticationService.getCurrentUser().getCustomer(), Long.parseLong(parameter.get("carId").toString()), DateUtil.toDate(parameter.get("startDate").toString()), DateUtil.toDate(parameter.get("endDate").toString()));
        } catch (NotAvailableException nAE) {
            return createStatusResponse(Response.Status.CONFLICT, nAE.getErrorMessge());
        }
        return createStatusResponse(Response.Status.CREATED, "rental created with Id=" + rental.getId());
    }

    @GET
    @Path("rentals")
    public Response findRentalHistoryForCustomer(@QueryParam("customerId") Long customerId) {
        List<Rental> history = rentalService.findRentalHistoryForCustomer(customerId);
        return createOkResponseFor(history);
    }


}
