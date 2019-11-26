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
@RequestMapping ( value = MealRestController.REST_MEAL_URL, produces = MediaType.APPLICATION_JSON_VALUE )
public class MealRestController extends AbstractMealController {

    public static final String REST_MEAL_URL = "/rest/meals";

    @GetMapping ( "/{id}" )
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @DeleteMapping ( "/{id}" )
    @ResponseStatus ( HttpStatus.NO_CONTENT )
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @PostMapping ( consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI mealURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_MEAL_URL + "/{id}")
                .buildAndExpand(created).toUri();
        return ResponseEntity.created(mealURL).body(created);
    }

    @PostMapping ( value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE )
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @GetMapping ( "/filter" )
    public List<MealTo> getBetween(@DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                   @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }


}