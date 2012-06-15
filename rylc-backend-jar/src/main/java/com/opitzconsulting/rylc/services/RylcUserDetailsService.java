package com.opitzconsulting.rylc.services;

import com.opitzconsulting.rylc.domain.RylcUserDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import static java.lang.String.format;

@Service
public class RylcUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        try {
            return (RylcUserDetails) entityManager
                    .createQuery("select u from RylcUserDetails u where u.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new UsernameNotFoundException(format("user with username [%s] not found", username));
        }
    }

}
