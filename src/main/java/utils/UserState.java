package utils;

import entities.User;

import java.util.ArrayList;

// all-in-one singleton for user state & update event
public class UserState {
    private static UserState instance;

    private final ArrayList<UserEventListener> listeners = new ArrayList<>();
    private User currentUser;

    private UserState() {
    }

    public static synchronized UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }

    public void addUpdateListener(UserEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UserEventListener listener) {
        listeners.remove(listener);
    }

    public void updateCurrentUser(User user) {
        currentUser = user;
        for (UserEventListener listener : listeners) {
            listener.onUserUpdate(user);
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
