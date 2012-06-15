package com.opitzconsulting.rylc.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaEntityRepository implements EntityRepository {

    private static final Log LOG = LogFactory.getLog(JpaEntityRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void cleanup() {
        LOG.info("cleanup database...");
        LOG.debug("delete rentals...");
        entityManager.createQuery("delete from Rental").executeUpdate();
        LOG.debug("delete cars...");
        entityManager.createQuery("delete from Car").executeUpdate();
        LOG.debug("delete cities...");
        entityManager.createQuery("delete from City").executeUpdate();
        LOG.debug("delete users...");
        entityManager.createQuery("delete from RylcUserDetails ").executeUpdate();
        LOG.debug("delete customers...");
        entityManager.createQuery("delete from Customer").executeUpdate();
    }

    @Override
    @Transactional
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    @Override
    @Transactional
    public <T> List<T> findAll(Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void persist(Object entity) {
        entityManager.persist(entity);
        entityManager.flush();
    }

}
