package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealsTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        Meal created = mealService.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatched(mealService.getAll(USER_ID), MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, created);
    }

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(MEALS_ID1, USER_ID);
        assertMatched(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotExistingMeal() throws Exception {
        mealService.get(1000009, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(MEALS_ID1, USER_ID);
        mealService.delete(MEALS_ID2, USER_ID);
        mealService.delete(MEALS_ID3, USER_ID);
        assertMatched(mealService.getAll(USER_ID), MEAL4, MEAL5, MEAL6);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExistingMeal() {
        mealService.delete(100009, USER_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> expected = new ArrayList<>(Arrays.asList(MEAL1, MEAL2, MEAL3));
        List<Meal> actual = mealService.getBetweenDates(LocalDate.of(2015, Month.JUNE, 1),
                LocalDate.of(2015, Month.JUNE, 30), USER_ID);
        assertMatched(actual, expected);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = mealService.getAll(USER_ID);
        assertMatched(all, MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setCalories(1000);
        updated.setDescription("Ужин");
        mealService.update(updated, USER_ID);
        assertMatched(mealService.get(MEALS_ID1, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotExistingMeal() {
        mealService.update(new Meal(1000009, LocalDateTime.now(), "", 0), USER_ID);
    }

}