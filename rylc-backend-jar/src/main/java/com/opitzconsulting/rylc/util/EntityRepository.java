package com.opitzconsulting.rylc.util;

import java.util.List;

public interface EntityRepository {

    void cleanup();

    <T> T find(Class<T> entityClass, Object primaryKey);

    <T> List<T> findAll(Class<T> entityClass);

    void persist(Object entity);

}
