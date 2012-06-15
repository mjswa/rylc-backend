package com.opitzconsulting.rylc.endpoints;

import com.opitzconsulting.rylc.domain.RylcUserDetails;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CustomerServiceEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

    @Test
    public void getCustomerWithUsername_notAuthenticated_returnsUnauthorized() {
        String username = "some_username";
        try {
            getRestTemplate().getForObject(uriFor(username), String.class);
            fail();
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getMessage().startsWith("401"));
        }
    }

    /*
     *  Test that our work around for using basic authentication as authentication scheme on client side is in place
     */
    @Test
    public void getCustomerWithUsername_wrongCredentials_returnsUnauthorized() {
        String username = "some_username";
        prepareRestTemplateForAuthentication("scott", "tiger");
        try {
            getRestTemplate().getForObject(uriFor(username), String.class);
            fail();
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getMessage().startsWith("401"));
        }
    }

    @Test
    public void getCustomerWithUsername_noMatchingCustomer_returnsNotFound() {
        String username = "some_username";
        try {
            getJson(pathFor(username));
            fail();
        } catch (HttpClientErrorException ex) {
            assertTrue(ex.getMessage().startsWith("404"));
        }
    }

    @Test
    public void getCustomerWithUsername_matchingCustomer_returnsCustomer() {
        RylcUserDetails user = getUserForAuthentication();
        String username = user.getUsername();
        assertJsonEquals(user.getCustomer(), pathFor(username));
    }

    private String pathFor(String username) {
        return format("/customers?username=%s", username);
    }

    private String uriFor(String username) {
        return getUriFor(pathFor(username));
    }

}
