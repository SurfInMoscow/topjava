package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl extends InMemoryBaseRepositoryImpl<User> implements UserRepository {//implements UserRepository {
    static final int USER_ID = 1;
    static final int ADMIN_ID = 2;
    /*private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> userMap = new ConcurrentHashMap<>();
*/
    /*@Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        User usr = get(id);
        return userMap.remove(id, usr);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        userMap.put(SecurityUtil.authUserId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userMap.get(id);
    }*/

    @Override
    public List<User> getAll() {
        //log.info("getAll");
        //return userMap.values().stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        //log.info("getByEmail {}", email);
        //return userMap.entrySet().stream().filter(p -> p.getValue().getEmail().equals(email)).collect(Collectors.toList()).get(0).getValue();
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
