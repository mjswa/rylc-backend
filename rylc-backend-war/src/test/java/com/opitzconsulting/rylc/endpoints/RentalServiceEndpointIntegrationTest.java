package com.opitzconsulting.rylc.endpoints;

import com.opitzconsulting.rylc.domain.*;
import com.opitzconsulting.rylc.util.DateUtil;
import flexjson.JSONDeserializer;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.*;

import static java.lang.String.format;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RentalServiceEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

    @Test
    public void findRentalHistoryForCustomer() {
        RylcUserDetails user = getUserForAuthentication();
        Customer customer = user.getCustomer();
        List<Rental> history = getEntityPersister().createRentalsFor(customer);
        assertJsonEquals(history, format("/rentals?customerId=%s", customer.getId()));
    }

    @Test
    public void findRentalHistoryForUnkownCustomer() {
        try {
            assertEquals("[]", getJson(format("/rentals?customerId=%s", -1L)));
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getMessage().startsWith("404"));
        }
    }

    @Test
    public void findCarTypes() throws JSONException {
        String json = getJson("/cartypes");
        JSONArray jsonArray = new JSONArray(json);
        assertEquals("MINI", jsonArray.get(0));
        assertEquals("ECONOMY", jsonArray.get(1));
        assertEquals("PREMIUM", jsonArray.get(2));
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void findCitiesForRentalSelection() {
        List<String> cityNames = new ArrayList<String>();
        cityNames.add("Berlin");
        cityNames.add("Hamburg");
        getEntityPersister().createCities(cityNames.get(0), cityNames.get(1));
        Set<City> expectedCities = new HashSet<City>(getEntityRepository().findAll(City.class));
        Set<City> actualCities = new HashSet<City>((List<City>) new JSONDeserializer()
                .use(null, ArrayList.class)
                .use("values", City.class)
                .deserialize(getJson("/cities")));
        assertEquals(expectedCities, actualCities);
    }

    @Test
    public void findAvailableCars() {
        String city = "Hamburg";
        Date startDate = new Date();
        Date endDate = new Date();
        BigDecimal maxPrice = BigDecimal.valueOf(100);
        Car car = getEntityPersister().createAvailableCarFor(city, maxPrice);
        String path = format("/availableCars?cityId=%s&startDate=%s&endDate=%s&maxPrice=%s",
                car.getHomeLocation().getId(),
                DateUtil.getDateAsString(startDate),
                DateUtil.getDateAsString(endDate),
                maxPrice);
        List foundCars = new JSONDeserializer<List>().deserialize(getJson(path), List.class);
        assertTrue(foundCars.size() == 1);
        Car foundCar = new JSONDeserializer<Car>().deserialize(foundCars.get(0).toString(), Car.class);
        assertEquals(car.getId(), foundCar.getId());
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void rentCar() {
        String city = "Hamburg";
        Date startDate = DateUtil.currentDateOnMidnight();
        Date endDate = DateUtil.currentDateOnMidnight();
        BigDecimal maxPrice = BigDecimal.valueOf(100.0);
        Car car = getEntityPersister().createAvailableCarFor(city, maxPrice);
        Map parameters = new HashMap();
        parameters.put("carId", car.getId());
        parameters.put("startDate", DateUtil.getDateAsString(startDate));
        parameters.put("endDate", DateUtil.getDateAsString(endDate));
        JSONObject jsonObject = new JSONObject(parameters);
        String jsonBody = jsonObject.toString();
        String path = "/rental";
        ResponseEntity<String> response = getJsonResponse(path, HttpMethod.POST, jsonBody);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
