package com.jbr.sandwich.control;

import com.jbr.sandwich.data.IngredientType;
import com.jbr.sandwich.dataaccess.IngredientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientTypeController {
    private final IngredientTypeRepository ingredientTypeRepository;

    @Autowired
    public IngredientTypeController(IngredientTypeRepository repository) {
        this.ingredientTypeRepository = repository;
    }

    @GetMapping("/ingredients/type")
    public List<IngredientType> getIngredients() {
        return ingredientTypeRepository.findAll();
    }
}
