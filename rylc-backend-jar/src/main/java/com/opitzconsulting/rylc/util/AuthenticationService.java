package com.opitzconsulting.rylc.util;

import com.opitzconsulting.rylc.domain.RylcUserDetails;

public interface AuthenticationService {
    public RylcUserDetails getCurrentUser();
}
