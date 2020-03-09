package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.to.MealTo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping(value = "/ajax/meal", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealAjaxController extends AbstractMealController {
    @GetMapping
    @Override
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid MealTo mealTo, BindingResult result) {
        if (result.hasErrors()) {
            StringJoiner joiner = new StringJoiner("<br>");
            result.getFieldErrors().forEach(fe -> {
                String msg = fe.getDefaultMessage();
                if (msg != null) {
                    if (!msg.startsWith(fe.getField())) {
                        msg = fe.getField() + ' ' + msg;
                    }
                    joiner.add(msg);
                }
            });
            return ResponseEntity.unprocessableEntity().body(joiner.toString());
        }
       if (mealTo.isNew()) {
           super.save(mealTo);
       } else {
          super.update(mealTo);
       }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    @Override
    public List<MealTo> getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                   @RequestParam(value = "startTime", required = false) LocalTime startTime,
                                   @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                   @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
