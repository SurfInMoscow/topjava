package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static ru.javawebinar.topjava.web.JspCreateMealController.createMeal;
import static ru.javawebinar.topjava.web.JspEditMealController.editMeal;

@Controller
public class JspMealController {
    private final MealRestController mealRestController;

    @Autowired
    public JspMealController(MealRestController mealRestController) {
        this.mealRestController = mealRestController;
    }

    @GetMapping("/meals")
    public String getMeals(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", mealRestController.getAll());
            return "meals";
        }
        Meal m;
        switch (action) {
            case "delete":
                return deleteMeal(request);
            case "new":
                return createMeal();
            case "edit":
                m = mealRestController.get(getId(request));
                request.setAttribute("meals", m);
                return editMeal();
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal.");
        }
    }

    public String deleteMeal(HttpServletRequest request) {
        mealRestController.delete(getId(request));
        request.setAttribute("meals", mealRestController.getAll());
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @PostMapping("/meals")
    public String setMeals(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
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
            model.addAttribute("meals", mealRestController.getBetween(startDate, startTime, endDate, endTime));
            return "meals";
        } else if (id == null) {
            m = new Meal(TimeUtil.stringToLocalDateTime(dateTime), description, Integer.parseInt(calories));
            mealRestController.save(m);
            model.addAttribute("meals", mealRestController.getAll());
            return "redirect:meals";
        } else {
            m = mealRestController.get(Integer.parseInt(id));
            m.setDateTime(TimeUtil.stringToLocalDateTime(dateTime));
            m.setDescription(description);
            m.setCalories(Integer.parseInt(calories));
            mealRestController.update(m);
            model.addAttribute("meals", mealRestController.getAll());
            return "redirect:meals";
        }
    }
}
