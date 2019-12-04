package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JspEditMealController {
    @GetMapping("/editMeal")
    protected static String editMeal() {
        return "editMeal";
    }
}
