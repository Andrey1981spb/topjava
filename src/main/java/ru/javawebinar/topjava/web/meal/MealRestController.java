package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractController;

@Controller
public class MealRestController extends AbstractController {

    @Autowired
    protected MealRestController(MealService service) {
        super(service);
    }
}