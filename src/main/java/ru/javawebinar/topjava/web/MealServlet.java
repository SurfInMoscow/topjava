package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private SecurityUtil securityUtil;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        this.mealRestController = appCtx.getBean(MealRestController.class);
        this.securityUtil = new SecurityUtil();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to /meals.jsp");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String id = request.getParameter("id");
        Meal m;
        if (id == null) {
            m = new Meal(TimeUtil.stringToLocalDateTime(dateTime), description, Integer.parseInt(calories), securityUtil.authUserId());
            mealRestController.save(m);
            request.setAttribute("meals", mealRestController.getFilteredWithExcessByCycle(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
            response.sendRedirect("meals");
        } else {
            m = mealRestController.get(Integer.parseInt(id));
            m.setDateTime(TimeUtil.stringToLocalDateTime(dateTime));
            m.setDescription(description);
            m.setCalories(Integer.parseInt(calories));
            mealRestController.update(m);
            request.setAttribute("meals", mealRestController.getFilteredWithExcessByCycle(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
            response.sendRedirect("meals");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to /meals.jsp");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String user = request.getParameter("user");
        if (user != null && user.equals("admin")) {
            securityUtil.setAuthUserId(1);
        }
        if (action == null) {
            request.setAttribute("meals", mealRestController.getFilteredWithExcessByCycle(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request,response);
            return;
        }
        Meal m;
        switch (action) {
            case "delete":
                mealRestController.delete(Integer.parseInt(id));
                request.setAttribute("meals", mealRestController.getFilteredWithExcessByCycle(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                response.sendRedirect("meals");
                return;
            case "new":
                request.getRequestDispatcher("/createMeal.jsp").forward(request,response);
                return;
            case "edit":
                m = mealRestController.get(Integer.parseInt(id));
                request.setAttribute("meals", m);
                request.getRequestDispatcher("/editMeal.jsp").forward(request,response);
                return;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal.");
        }
    }
}
