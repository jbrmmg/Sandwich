package com.jbr.sandwich.control;

import com.jbr.sandwich.data.Ingredient;
import com.jbr.sandwich.data.IngredientType;
import com.jbr.sandwich.data.dtoIngredient;
import com.jbr.sandwich.dataaccess.IngredientRepository;
import com.jbr.sandwich.dataaccess.IngredientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class IngredientController {
    private final IngredientRepository ingredientRepository;
    private final IngredientTypeRepository ingredientTypeRepository;

    @Autowired
    public IngredientController(IngredientRepository repository,
                                IngredientTypeRepository ingredientTypeRepository) {
        this.ingredientRepository = repository;
        this.ingredientTypeRepository = ingredientTypeRepository;
    }

    @GetMapping("/ingredients")
    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/ingredient")
    public Ingredient getIngredient(@RequestParam Long id) throws Exception {
        Optional<Ingredient> currentIngredient = ingredientRepository.findById(id);

        if(!currentIngredient.isPresent()) {
            throw new Exception("Ingredient not found.");
        }

        return currentIngredient.get();
    }

    @PostMapping("/ingredient")
    public Ingredient createIngredient(@RequestBody dtoIngredient newIngredient) throws Exception {
        Optional<IngredientType> ingredientType = ingredientTypeRepository.findById(newIngredient.getType().getId());

        if(!ingredientType.isPresent()) {
            throw new Exception("Invalid ingredient type.");
        }

        Ingredient dbIngredient = new Ingredient(newIngredient.getName());
        dbIngredient.setType(ingredientType.get());

        ingredientRepository.save(dbIngredient);

        return dbIngredient;
    }

    @PutMapping("/ingredient")
    public Ingredient updateIngredient(@RequestBody dtoIngredient newIngredient) throws Exception {
        Optional<Ingredient> currentIngredient = ingredientRepository.findById(newIngredient.getId());

        if(!currentIngredient.isPresent()) {
            throw new Exception("Invalid ingredient id.");
        }

        Optional<IngredientType> ingredientType = ingredientTypeRepository.findById(newIngredient.getType().getId());

        if(!ingredientType.isPresent()) {
            throw new Exception("Invalid ingredient type.");
        }

        currentIngredient.get().setName(newIngredient.getName());
        currentIngredient.get().setType(ingredientType.get());

        ingredientRepository.save(currentIngredient.get());

        return currentIngredient.get();
    }

    @DeleteMapping("/ingredient")
    public Ingredient deleteIngredient(@RequestBody dtoIngredient deleteIngredient) throws Exception {
        Optional<Ingredient> currentIngredient = ingredientRepository.findById(deleteIngredient.getId());

        if(!currentIngredient.isPresent()) {
            throw new Exception("Invalid ingredient id.");
        }

        ingredientRepository.delete(currentIngredient.get());

        return currentIngredient.get();
    }
}
