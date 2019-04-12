package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl extends InMemoryBaseRepositoryImpl<User> implements UserRepository {//implements UserRepository {
    /*static final int USER_ID = 1;
    static final int ADMIN_ID = 2;*/

    public void init() {
        entryMap.clear();
        entryMap.put(UserTestData.USER_ID, UserTestData.USER);
        entryMap.put(UserTestData.ADMIN_ID, UserTestData.ADMIN);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        //return userMap.entrySet().stream().filter(p -> p.getValue().getEmail().equals(email)).collect(Collectors.toList()).get(0).getValue();
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
