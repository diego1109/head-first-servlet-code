package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Admin extends HttpServlet {
    private static final long SerialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handRequest(request, response);
    }

    private void handRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.write("<html><body><div id='servletResponse' style='text-align: center;'>");
        out.write("<h2>Java Sevlet Filter Example</h2>");
        out.write("<p style='color: green; font-size: large;'>Welcome, Administrator!</p>");
        out.write("</div></body></html>");
        out.close();
    }
}
