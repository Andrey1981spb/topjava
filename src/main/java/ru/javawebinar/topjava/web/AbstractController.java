package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public abstract class AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    public MealService service;

    @Autowired
    protected AbstractController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        log.info("get meal {} for user {}", id, SecurityUtil.authUserId());
        return service.get(id, SecurityUtil.authUserId());
    }

    protected String delete(int id) {
        log.info("delete meal {} for user {}", id, SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.authUserId());
        return null;
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        log.info("create {} for user {}", meal, SecurityUtil.authUserId());
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, SecurityUtil.authUserId());
        service.update(meal, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll() {
        log.info("getAll for user {}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, SecurityUtil.authUserId());

        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, SecurityUtil.authUserId());
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}