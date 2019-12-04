package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JspCreateMealController {
    @GetMapping("/createMeal")
    protected static String createMeal() {
        return "createMeal";
    }
}
