package ru.javawebinar.topjava.web.user;

import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class ProfileRestController extends AbstractUserController {
    @Override
    public User get(int id) {
        return super.get(authUserId());
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public void update(User user, int id) {
        super.update(user, id);
    }
}
