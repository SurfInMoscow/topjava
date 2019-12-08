package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;

@Controller
public class JspCreateMealController {

    protected static String createMeal() {
        return "createMeal";
    }

}
