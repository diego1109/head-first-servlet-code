package com.example;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieTest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("username");
        Cookie cookie = new Cookie("username", name);
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);

        RequestDispatcher view = request.getRequestDispatcher("cookieresult.jsp");
        view.forward(request, response);
    }
}