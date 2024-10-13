package com.example.zerosoles.auth;

import androidx.annotation.Nullable;

import com.example.zerosoles.data.entity.User;

import java.util.LinkedList;
import java.util.List;

public class SessionManager {

    private static SessionManager instance;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private User loggedInUser;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        for (LoggedInUserChangedHandler handler : loggedInUserChangedHandlers) {
            handler.handleLoggedInUserChanged(loggedInUser);
        }
    }

    public interface LoggedInUserChangedHandler {
        void handleLoggedInUserChanged(@Nullable User user);
    }

    private final List<LoggedInUserChangedHandler> loggedInUserChangedHandlers = new LinkedList<>();

    public void addListener(LoggedInUserChangedHandler handler) {
        loggedInUserChangedHandlers.add(handler);
    }

    public void removeListener(LoggedInUserChangedHandler handler) {
        loggedInUserChangedHandlers.remove(handler);
    }
}
