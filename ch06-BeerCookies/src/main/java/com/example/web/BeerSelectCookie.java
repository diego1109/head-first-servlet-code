package com.example.web;

import com.example.model.BeerAdvice;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BeerSelectCookie extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        HttpSession session = request.getSession(false);

        String color = "";
        color = request.getParameter("color");
        String body = "";
        String size = "";

        PrintWriter out = response.getWriter();

        if (session == null) {
            // get the first value: color
            RequestDispatcher rd = request.getRequestDispatcher("result1.jsp");
            rd.forward(request, response);
            out.println(request.getParameter("color"));
        } else {
            if (request.getParameter("body") != null && request.getParameter("sizes") == null) {
                body = request.getParameter("body");
                out.println(request.getParameter("body"));
                RequestDispatcher rd = request.getRequestDispatcher("result2.jsp");
                rd.forward(request, response);
            } else if (request.getParameter("sizes") != null) {
                size = request.getParameter("sizes");
                request.setAttribute("advice", "INDIO!!!");
                RequestDispatcher rd = request.getRequestDispatcher("resultFinal.jsp");
                rd.forward(request, response);
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        // The cookie
        HttpSession session = request.getSession(false);
        String color = "";
        color = request.getParameter("color");
        String body = "";
        String size = "";
        if (session == null) {
            // Get the first value: color.
            RequestDispatcher rd = request.getRequestDispatcher("result1.jsp");
            rd.forward(request, response);

        } else {
            if (request.getParameter("body") != null && request.getParameter("sizes") == null) {
                body = request.getParameter("body");
                RequestDispatcher rd = request.getRequestDispatcher("result2.jsp");
                rd.forward(request, response);
            } else if (request.getParameter("sizes") != null) {
                // Make BeerSelection object
                size = request.getParameter("sizes");
                BeerAdvice ba = new BeerAdvice();
                List<String> brands = ba.getBrands(color, body, size);
                request.setAttribute("advice", brands);
                RequestDispatcher rd = request.getRequestDispatcher("resultFinal.jsp");
                rd.forward(request, response);
            }
        }
    }
}
