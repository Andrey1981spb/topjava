package ru.javawebinar.topjava.repository.jpa;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em2;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(em2.find(User.class, userId));
        if (meal.isNew()) {
            em2.persist(meal);
            return meal;
        } else {
            return em2.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em2.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        return (Meal) em2.createNamedQuery(Meal.GET)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getSingleResult();
    }

    @Override
    @Transactional
    public List<Meal> getAll(int userId) {
        return em2.createNamedQuery(Meal.GET_ALL_SORTED, Meal.class).setParameter("user_id", userId).getResultList();
    }

    @Override
    @Transactional
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em2.createNamedQuery(Meal.BETWEEN, Meal.class)
                .setParameter("min", startDate)
                .setParameter("max", endDate)
                .setParameter("user_id", userId)
                .getResultList();
    }
}