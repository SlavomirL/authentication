package com.greenfoxacademy.backendapp.repositories;

import com.greenfoxacademy.backendapp.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findMealById(long id);
}
