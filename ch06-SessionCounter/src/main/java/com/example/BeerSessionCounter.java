package com.example;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class BeerSessionCounter implements HttpSessionListener {

    private static int activeSessions = 0;

    HttpSession session;

    public static int getActiveSessions() {
        return activeSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        activeSessions++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        activeSessions--;
    }
}
