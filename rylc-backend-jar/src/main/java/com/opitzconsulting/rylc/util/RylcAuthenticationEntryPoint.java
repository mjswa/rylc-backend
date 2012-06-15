package com.opitzconsulting.rylc.util;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * We use a custom authentication entry point to modify the backend behaviour on access with bad basic authentication
 * credentials. To avoid the browser popup asking for basic authentication credentials, the backend does not send an
 * authentication challenge (WWW-Authenticate header field).
 */
public class RylcAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

}
