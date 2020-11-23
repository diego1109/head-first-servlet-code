package com.example.web;

import com.example.model.BeerExport;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeerSelect extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Beer Selection Advice <br>");
        String c = request.getParameter("color");
        // out.println("<br> Got beer color " + c);

        BeerExport beerExport = new BeerExport();
        List result = beerExport.getBrands(c);

        result.forEach(item -> out.println("<br>try: " + item));

    }
}
