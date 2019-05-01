package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class MealServlet extends HttpServlet {

    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        this.mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        Meal m;
        if ("filter".equals(action)) {
            LocalDate startDate = TimeUtil.parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = TimeUtil.parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = TimeUtil.parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = TimeUtil.parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("meals", mealRestController.getBetween(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request,response);
        } else if (id == null) {
            m = new Meal(TimeUtil.stringToLocalDateTime(dateTime), description, Integer.parseInt(calories));
            mealRestController.save(m);
            request.setAttribute("meals", mealRestController.getAll());
            response.sendRedirect("meals");
        } else {
            m = mealRestController.get(Integer.parseInt(id));
            m.setDateTime(TimeUtil.stringToLocalDateTime(dateTime));
            m.setDescription(description);
            m.setCalories(Integer.parseInt(calories));
            mealRestController.update(m);
            request.setAttribute("meals", mealRestController.getAll());
            response.sendRedirect("meals");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", mealRestController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request,response);
            return;
        }
        Meal m;
        switch (action) {
            case "delete":
                mealRestController.delete(getId(request));
                request.setAttribute("meals", mealRestController.getAll());
                response.sendRedirect("meals");
                return;
            case "new":
                request.getRequestDispatcher("/createMeal.jsp").forward(request,response);
                return;
            case "edit":
                m = mealRestController.get(getId(request));
                request.setAttribute("meals", m);
                request.getRequestDispatcher("/editMeal.jsp").forward(request,response);
                return;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal.");
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
