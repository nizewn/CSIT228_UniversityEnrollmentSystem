package utils;

import entities.User;

public interface UserEventListener {
    void onUserUpdate(User user);
}
