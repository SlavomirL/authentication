package com.greenfoxacademy.backendapp.services;

import com.greenfoxacademy.backendapp.dtos.DateDTO;
import com.greenfoxacademy.backendapp.dtos.MealDTO;
import com.greenfoxacademy.backendapp.models.Meal;
import com.greenfoxacademy.backendapp.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    final MealRepository mealRepository;

    public Meal createMeal(MealDTO mealDTO){
        Meal meal = new Meal();
        meal.setName(mealDTO.getName());
        meal.setCalories(mealDTO.getCalories());
        meal.setLocalDate(LocalDate.now());
        mealRepository.save(meal);
        return meal;
    }

    public List<Meal> getAllMeals(){
        return mealRepository.findAll();
    }

    public boolean isValidId(long id){
        return !mealRepository.findById(id).isEmpty();
    }

    public Meal updateMealById(long id, MealDTO mealDTO){
        Meal meal = mealRepository.findMealById(id);
        meal.setName(mealDTO.getName());
        meal.setCalories(mealDTO.getCalories());
        meal.setLocalDate(LocalDate.now());
        mealRepository.save(meal);
        return meal;
    }

    public void deleteMealById(long id){
        mealRepository.deleteById(id);
    }

    public Meal getMealById(long id){
        return mealRepository.findMealById(id);
    }

    public double sumCaloriesByDate(DateDTO dateDTO) {
        return mealRepository.findAll()
                    .stream()
                    .filter(x -> x.getLocalDate().equals(dateDTO.getLocalDate()))
                    .mapToDouble(Meal::getCalories)
                    .sum();
    }
}
