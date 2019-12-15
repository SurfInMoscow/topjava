package ru.javawebinar.topjava.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    protected JpaUtil jpaUtil;

    @BeforeEach
    public void clear2ndLevelCache() throws Exception {
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
