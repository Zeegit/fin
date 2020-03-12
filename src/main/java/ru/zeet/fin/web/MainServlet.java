package ru.zeet.fin.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private Map<String, Controller> controllers;
    private ObjectMapper om;

    public MainServlet() {
        this.controllers = controllers;
        this.controllers.put("/login", new LoginController());

        this.om = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        System.out.println(req.getRequestURI());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Controller controller = controllers.get(uri);
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            Object request = om.readValue(req.getInputStream(), controller.getRequestClass());
            Object response = controller.execute(request);
            om.writeValue(resp.getOutputStream(), response);
            resp.setContentType("application/json");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }

    /*
        //String name = req.getParameter("name");
        PrintWriter writer = resp.getWriter();

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            // resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect("/login");
        } else {
            writer.println("Hello " + req.getSession().getAttribute("userId"));
        }
    }*/
}
