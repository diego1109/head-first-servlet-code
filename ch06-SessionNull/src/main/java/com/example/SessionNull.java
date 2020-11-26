package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionNull extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("test session <br>");

        HttpSession session = request.getSession(false);
        if (session == null) {
            out.println("no session is available");
            out.println("please make one ......");

            session = request.getSession();
        } else {
            out.println("there is a session");
        }

    }
}
