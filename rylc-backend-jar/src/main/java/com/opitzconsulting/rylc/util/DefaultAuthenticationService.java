package com.opitzconsulting.rylc.util;

import com.opitzconsulting.rylc.domain.RylcUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationService implements AuthenticationService {

    public RylcUserDetails getCurrentUser() {
        RylcUserDetails user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            user = (RylcUserDetails) authentication.getPrincipal();
        }
        return user;
    }

}
