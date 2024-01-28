package com.greenfoxacademy.backendapp.controllers;

import com.greenfoxacademy.backendapp.dtos.DateDTO;
import com.greenfoxacademy.backendapp.dtos.MealDTO;
import com.greenfoxacademy.backendapp.dtos.SumDTO;
import com.greenfoxacademy.backendapp.exceptions.MealNotFoundException;
import com.greenfoxacademy.backendapp.services.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RestApiController {

    final MealService mealService;

    @PostMapping("/add")
    public ResponseEntity<?> addMeal(@RequestBody MealDTO mealDTO){

        if (mealDTO.getName() == null || mealDTO.getCalories() == null ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mealService.createMeal(mealDTO));
    }

    @GetMapping("/meals")
    public ResponseEntity<?> getAll(){

        return ResponseEntity.ok(mealService.getAllMeals());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMeal(@PathVariable long id, @RequestBody MealDTO mealDTO){

        if (mealService.isValidId(id)){
            return ResponseEntity.notFound().build();
        }
        if (mealDTO.getName() == null || mealDTO.getCalories() == null ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mealService.updateMealById(id, mealDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMeal(@PathVariable long id){

        if (mealService.isValidId(id)){
            return ResponseEntity.notFound().build();
        }
        mealService.deleteMealById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meal/{id}")
    public ResponseEntity<?> getMeal(@PathVariable long id){

        if (mealService.isValidId(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping("/calories")
    public ResponseEntity<?> getSumOfCalories(@RequestBody DateDTO dateDTO) {
        throw new MealNotFoundException("this is just a test of our custom exception. otherwise this line is nonsense");

        // this code is commented out because the line above makes it unreachable. this is for me to see the exception handling purposes only
//        if (dateDTO.getLocalDate() == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().body(
//                new SumDTO(dateDTO.getLocalDate(),
//                        mealService.sumCaloriesByDate(dateDTO)
//                )
//        );
    }
}
