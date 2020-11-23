package com.example.web;

import com.example.model.BeerExport;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeerSelect extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // response.setContentType("text/html");
        // PrintWriter out = response.getWriter();
        // out.println("Beer Selection Advice <br>");
        String c = request.getParameter("color");

        BeerExport beerExport = new BeerExport();
        List result = beerExport.getBrands(c);

        // result.forEach(item -> out.println("<br>try: " + item));

        // 将业务执行结果保存到 servletRequest 中。
        request.setAttribute("styles", result);
        // 创建 request 分发器
        RequestDispatcher view = request.getRequestDispatcher("result.jsp");

        view.forward(request, response);

    }
}
