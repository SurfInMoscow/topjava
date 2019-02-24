package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.Counter;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");
        List<User> users = new ArrayList<>();
        users.add(new User(Counter.getIncrement(),"Pavel", "p.vorobyev@inbox.ru", "12345", Role.ROLE_ADMIN));
        users.add(new User(Counter.getIncrement(),"Boris", "p.vorobyev@mail.ru", "12345", Role.ROLE_ADMIN));
        users.add(new User(Counter.getIncrement(),"Andrey", "p.vorobyev@ya.ru", "12345", Role.ROLE_ADMIN));
        UserRepository repository = new InMemoryUserRepositoryImpl();
        users.forEach(repository::save);

        System.out.println(repository.getAll().toString());
        System.out.println();
        System.out.println(repository.getByEmail("p.vorobyev@ya.ru").toString());

        repository.delete(users.get(0).getId());
        System.out.println(repository.getAll().size());
    }
}
