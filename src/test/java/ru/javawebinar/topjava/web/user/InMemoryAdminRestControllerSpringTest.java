package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(locations = {"classpath:spring/spring-inmemory.xml", "classpath:spring/spring-app.xml"})
public class InMemoryAdminRestControllerSpringTest {

  /*  @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepositoryImpl repository;

    @BeforeEach
    public void setUp() {
        repository.init();
    }

    @Test
    public void delete() throws Exception {
        controller.delete(UserTestData.USER_ID);
        Collection<User> users = controller.getAll();
        assertEquals(users.size(), 1);
        assertEquals(users.iterator().next(), UserTestData.ADMIN);
    }

    @Test
    public void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> controller.delete(5));
    }*/
}
