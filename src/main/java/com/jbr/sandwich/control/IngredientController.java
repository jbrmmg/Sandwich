package com.jbr.sandwich.control;

import com.jbr.sandwich.data.Ingredient;
import com.jbr.sandwich.data.IngredientType;
import com.jbr.sandwich.data.dtoIngredient;
import com.jbr.sandwich.dataaccess.IngredientRepository;
import com.jbr.sandwich.dataaccess.IngredientTypeRepository;
import com.jbr.sandwich.exception.SandwichObjectAlreadyExists;
import com.jbr.sandwich.exception.SandwichObjectNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class IngredientController {
    private static final Logger LOG = LoggerFactory.getLogger(IngredientController.class);

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
    public Ingredient getIngredient(@RequestParam Long id) {
        Optional<Ingredient> currentIngredient = ingredientRepository.findById(id);

        if(!currentIngredient.isPresent()) {
            throw new SandwichObjectNotFound("ingredient", id.toString());
        }

        LOG.info("Get Ingredient: {}", currentIngredient.get());

        return currentIngredient.get();
    }

    @PostMapping("/ingredient")
    public Ingredient createIngredient(@RequestBody dtoIngredient newIngredient) {
        Optional<IngredientType> ingredientType = ingredientTypeRepository.findById(newIngredient.getType().getId());

        if(!ingredientType.isPresent()) {
            throw new SandwichObjectNotFound("ingredient type", newIngredient.getId().toString());
        }

        List<Ingredient> existing = ingredientRepository.findByName(newIngredient.getName());
        if(existing.size() > 0) {
            throw new SandwichObjectAlreadyExists("ingredient", newIngredient.getName());
        }

        Ingredient dbIngredient = new Ingredient(newIngredient.getName());
        dbIngredient.setType(ingredientType.get());

        LOG.info("Create new ingredient. {}", dbIngredient);

        ingredientRepository.save(dbIngredient);

        return dbIngredient;
    }

    @PutMapping("/ingredient")
    public Ingredient updateIngredient(@RequestBody dtoIngredient updateIngredient) {
        Optional<Ingredient> currentIngredient = ingredientRepository.findById(updateIngredient.getId());

        if(!currentIngredient.isPresent()) {
            throw new SandwichObjectNotFound("ingredient", updateIngredient.getId().toString());
        }

        Optional<IngredientType> ingredientType = ingredientTypeRepository.findById(updateIngredient.getType().getId());

        if(!ingredientType.isPresent()) {
            throw new SandwichObjectNotFound("ingredient type", updateIngredient.getType().getId());
        }

        currentIngredient.get().setName(updateIngredient.getName());
        currentIngredient.get().setType(ingredientType.get());

        LOG.info("Update ingredient. {}", currentIngredient.get());

        ingredientRepository.save(currentIngredient.get());

        return currentIngredient.get();
    }

    @DeleteMapping("/ingredient")
    public Ingredient deleteIngredient(@RequestBody dtoIngredient deleteIngredient) {
        Optional<Ingredient> currentIngredient = ingredientRepository.findById(deleteIngredient.getId());

        if(!currentIngredient.isPresent()) {
            throw new SandwichObjectNotFound("ingredient", deleteIngredient.getId().toString());
        }

        LOG.info("Delete ingredient. {}", currentIngredient.get());

        ingredientRepository.delete(currentIngredient.get());

        return currentIngredient.get();
    }
}
