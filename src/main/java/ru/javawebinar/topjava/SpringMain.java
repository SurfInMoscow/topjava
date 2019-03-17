package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Beans in context: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminRestController = appCtx.getBean(AdminRestController.class);
            adminRestController.create(new User(null, "Pavel", "p.vorobyev@inbox.ru", "12345", Role.ROLE_ADMIN));
            System.out.println(adminRestController.getAll().toString());

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            /*mealRestController.save(new Meal(LocalDateTime.of(2019, 02, 28, 14, 22), "обед", 500, SecurityUtil.authUserId()));
            mealRestController.save(new Meal(LocalDateTime.of(2019, 02, 28, 14, 22), "ужин", 500, SecurityUtil.authUserId()));
            mealRestController.save(new Meal(LocalDateTime.of(2019, 02, 28, 14, 22), "чай", 100, SecurityUtil.authUserId()));
            mealRestController.save(new Meal(LocalDateTime.of(2019, 02, 28, 14, 22), "сладкое", 901, SecurityUtil.authUserId()));*/
            mealRestController.getAll().forEach(meal -> System.out.println(meal.toString()));
            System.out.println("---------------------------------------------------------------");
            //mealRestController.getFilteredWithExcessByCycle(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY).forEach(mealTo -> System.out.println(mealTo.toString()));
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealTo> filteredMealsWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExcess.forEach(System.out::println);
        }
    }
}
