package com.jbr.sandwich.control;

import com.jbr.sandwich.data.Ingredient;
import com.jbr.sandwich.dataaccess.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository repository) {
        this.ingredientRepository = repository;
    }

    @GetMapping("/ingredients")
    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }
}
