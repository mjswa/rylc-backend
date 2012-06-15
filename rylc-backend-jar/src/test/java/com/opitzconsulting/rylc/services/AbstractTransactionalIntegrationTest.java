package com.opitzconsulting.rylc.services;

import com.opitzconsulting.rylc.test.EntityPersister;
import com.opitzconsulting.rylc.util.EntityRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "classpath:/testContext.xml"})
public abstract class AbstractTransactionalIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private EntityPersister entityPersister;

    @Before
    public final void setUp() {
        entityRepository.cleanup();
    }

    protected EntityRepository getEntityRepository() {
        return entityRepository;
    }

    protected EntityPersister getEntityPersister() {
        return entityPersister;
    }
}
