package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = MealsUtil.storage;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to /meals.jsp");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredWithExcessByCycle(storage.getAll(),  LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request,response);
            return;
        }
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(id));
                request.setAttribute("meals", MealsUtil.getFilteredWithExcessByCycle(storage.getAll(),  LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));
                response.sendRedirect("meals");
                return;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal.");
        }
        //request.setAttribute("meals", MealsUtil.getFilteredWithExcessByCycle(storage.getAll(),  LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));
        //request.getRequestDispatcher("/meals.jsp").forward(request,response);
    }
}
