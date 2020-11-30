package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Login implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        out.write("<html><body><div id='servletResponse' style='text-align: center;'>");

        String password = request.getParameter("password");
        System.out.println("Password Is?= " + password);

        if (password != null && password.equals("admin")) {
            chain.doFilter(request, response);
            out.write("--- after chian.doFilter() ---");
        } else {
            out.print(
                    "<p id='errMsg' style='color: red; font-size: larger;'>Username Or Password Is Invalid. Please Try Again ....!</p>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
