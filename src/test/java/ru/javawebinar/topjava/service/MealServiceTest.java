package ru.javawebinar.topjava.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private static final Logger log2 = getLogger(MealServiceTest.class);

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception {
        long time = timer(() -> {
            service.delete(MEAL1_ID, USER_ID);
            assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF delete-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        long time = timer(() -> {
        service.delete(1, USER_ID);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF deleteNotFound-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotOwn() throws Exception {
        long time = timer(() -> {
        service.delete(MEAL1_ID, ADMIN_ID);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF deleteNotOwn-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test
    public void create() throws Exception {
        long time = timer(() -> {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF create-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test
    public void get() throws Exception {
        long time = timer(() -> {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF get-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        long time = timer(() -> {
        service.get(1, USER_ID);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF getNotFound-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test(expected = NotFoundException.class)
    public void getNotOwn() throws Exception {
        long time = timer(() -> {
        service.get(MEAL1_ID, ADMIN_ID);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF getNotFound-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test
    public void update() throws Exception {
        long time = timer(() -> {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF update-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        long time = timer(() -> {
        service.update(MEAL1, ADMIN_ID);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF updateNotFound-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test
    public void getAll() throws Exception {
        long time = timer(() -> {
        assertMatch(service.getAll(USER_ID), MEALS);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF getAll-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    @Test
    public void getBetween() throws Exception {
        long time = timer(() -> {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
        }, TimeUnit.NANOSECONDS);
        log2.debug("TIME OF getBetween-TEST IS " + String.valueOf(time) + " NANOSECONDS");
    }

    private static long timer(Runnable method, TimeUnit timeUnit) {
        long time = System.nanoTime();
        method.run();
        time = System.nanoTime() - time;
        return TimeUnit.NANOSECONDS.convert(time, timeUnit);
    }
}