package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    protected final static String REST_URL = "/rest/meal";

    @GetMapping
    @Override
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    @Override
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/between")
    @Override
    public List<MealTo> getBetween(@RequestParam(defaultValue = "01-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam(defaultValue = "00:00") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                   @RequestParam(defaultValue = "3000-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @RequestParam(defaultValue = "23:59:59.999999999") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Override
    public void update(@RequestBody Meal meal) {
        super.update(meal);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable int id) {
        super.delete(id);
    }
}