package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
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

    private MealRepository mealRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRepository = MealsUtil.mealRepository;
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
        /*if (id == null) {
            m = new Meal(TimeUtil.stringToLocalDateTime(dateTime), description, Integer.parseInt(calories));
            mealRepository.save(m);
            request.setAttribute("meals", MealsUtil.getFilteredWithExcessByCycle(mealRepository.getAll(),  LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));
            response.sendRedirect("meals");
        } else {
            m = mealRepository.get(Integer.parseInt(id));
            m.setDateTime(TimeUtil.stringToLocalDateTime(dateTime));
            m.setDescription(description);
            m.setCalories(Integer.parseInt(calories));
            mealRepository.update(m);
            request.setAttribute("meals", MealsUtil.getFilteredWithExcessByCycle(mealRepository.getAll(),  LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));
            response.sendRedirect("meals");
        }*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to /meals.jsp");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredWithExcessByCycle(mealRepository.getAll(Integer.parseInt(userId)),  LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request,response);
            return;
        }
        Meal m;
        switch (action) {
            case "delete":
                mealRepository.delete(Integer.parseInt(userId), Integer.parseInt(id));
                request.setAttribute("meals", MealsUtil.getFilteredWithExcessByCycle(mealRepository.getAll(Integer.parseInt(userId)),  LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));
                response.sendRedirect("meals");
                return;
            case "new":
                request.getRequestDispatcher("/createMeal.jsp").forward(request,response);
                return;
            case "edit":
                m = mealRepository.get(Integer.parseInt(userId), Integer.parseInt(id));
                request.setAttribute("meals", m);
                request.getRequestDispatcher("/editMeal.jsp").forward(request,response);
                return;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal.");
        }
    }
}
