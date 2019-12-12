package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @GetMapping("/editMeal")
    public String editMeal(HttpServletRequest request, Model model) {
        model.addAttribute("meals", super.get(getId(request)));
        return "editMeal";
    }

    @GetMapping("/createMeal")
    public String createMeal() {
        return "createMeal";
    }

    @GetMapping("/filter")
    public String filterMeals(HttpServletRequest request, Model model) {
        LocalDate startDate = TimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = TimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = TimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = TimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @PostMapping
    public String editOrCreateMeal(HttpServletRequest request, Model model) {
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String id = request.getParameter("id");
        Meal m;
        if (id == null) {
            m = new Meal(TimeUtil.stringToLocalDateTime(dateTime), description, Integer.parseInt(calories));
            super.save(m);
        } else {
            m = super.get(getId(request));
            m.setDateTime(TimeUtil.stringToLocalDateTime(dateTime));
            m.setDescription(description);
            m.setCalories(Integer.parseInt(calories));
            super.update(m);
        }
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
