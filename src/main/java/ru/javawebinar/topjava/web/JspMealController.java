package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping ( "/meals" )
public class JspMealController extends AbstractController {

    @Autowired
    protected JspMealController(MealService service) {
        super(service);
    }

    @GetMapping ( "/delete/100002" )
    public String delete(HttpServletRequest request) {
        super.delete(100002);
        return "redirect:/meals";
    }

    @GetMapping ( "/create" )
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping ( "/update/100002" )
    public String update(HttpServletRequest request, Model model) {
        final Meal meal = service.get(100002, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping ( "/filter" )
    public String getBetween(HttpServletRequest request,
                             Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping ()
    public String getAll(Model model) {
        model.addAttribute("meals", service.getAll(SecurityUtil.authUserId()));
        return "meals";
    }

    @PostMapping ()
    public String setMeals(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            service.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }
}

