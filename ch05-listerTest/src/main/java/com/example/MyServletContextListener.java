package com.example;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext sc = event.getServletContext();
        String dogBreed = sc.getInitParameter("breed");

        Dog d = new Dog(dogBreed);

        sc.setAttribute("dog", d);
    }
}