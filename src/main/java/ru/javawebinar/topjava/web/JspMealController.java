package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class JspMealController {
    private final MealService mealService;

    @Autowired
    public JspMealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/meals")
    public String getMeals(HttpServletRequest request) {
        String paramId = "0";
        int id = Integer.parseInt(paramId);
        int usrId = SecurityUtil.authUserId();
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getWithExcess(mealService.getAll(usrId), SecurityUtil.authUserCaloriesPerDay()));
            return "meals";
        }
        Meal m;
        switch (action) {
            case "delete":
                mealService.delete(usrId, id);
                request.setAttribute("meals", mealService.getAll(usrId));
                return "redirect:meals";
            case "new":
                return createMeal();
            case "edit":
                m = mealService.get(usrId, id);
                request.setAttribute("meals", m);
                return editMeal();
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal.");
        }
    }

    @GetMapping("/createMeal")
    private String createMeal() {
        return "createMeal";
    }

    @GetMapping("/editMeal")
    private String editMeal() {
        return "editMeal";
    }
}
