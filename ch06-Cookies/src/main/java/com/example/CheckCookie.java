package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckCookie extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      Stream.of(cookies).forEach(cookie -> {
        if (cookie.getName().equals("username")) {
          out.println("cookie: " + cookie.toString()+ "<br>");  
          out.println("Hello " + cookie.getValue());
        }
      });
    }
  }
}