package com.jbr.sandwich.control;

import com.jbr.sandwich.data.IngredientType;
import com.jbr.sandwich.data.dtoIngredientType;
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
public class IngredientTypeController {
    private static final Logger LOG = LoggerFactory.getLogger(IngredientTypeController.class);

    private final IngredientTypeRepository ingredientTypeRepository;

    @Autowired
    public IngredientTypeController(IngredientTypeRepository repository) {
        this.ingredientTypeRepository = repository;
    }

    @GetMapping("/ingredients/types")
    public @ResponseBody List<IngredientType> getIngredients() {
        return ingredientTypeRepository.findAll();
    }

    @GetMapping("/ingredients/type")
    public @ResponseBody IngredientType getIngredientType(@RequestParam String id) {
        Optional<IngredientType> currentType = ingredientTypeRepository.findById(id);

        if(!currentType.isPresent()) {
            throw new SandwichObjectNotFound("ingredient", id);
        }

        LOG.info("Get type: {}", currentType.get());

        return currentType.get();
    }

    @PostMapping("/ingredients/type")
    public @ResponseBody IngredientType createIngredientType(@RequestBody dtoIngredientType newType) {
        Optional<IngredientType> existingType = ingredientTypeRepository.findById(newType.getId());

        if(existingType.isPresent()) {
            throw new SandwichObjectAlreadyExists("ingredient type", newType.getId());
        }

        LOG.info("Create new ingredient type. {}", newType);

        IngredientType newDbType = new IngredientType();
        newDbType.setId(newType.getId());
        newDbType.setOrder(newType.getOrder());
        newDbType.setSelectionId(newType.getSelection());

        ingredientTypeRepository.save(newDbType);

        return newDbType;
    }

    @PutMapping("/ingredients/type")
    public @ResponseBody IngredientType updateIngredientType(@RequestBody dtoIngredientType newType)  {
        Optional<IngredientType> existingType = ingredientTypeRepository.findById(newType.getId());

        if(!existingType.isPresent()) {
            throw new SandwichObjectNotFound("ingredient type", newType.getId());
        }

        LOG.info("Update ingredient type {}.", newType);

        existingType.get().setId(newType.getId());
        existingType.get().setOrder(newType.getOrder());
        existingType.get().setSelectionId(newType.getSelection());

        ingredientTypeRepository.save(existingType.get());

        return existingType.get();
    }

    @DeleteMapping("/ingredients/type")
    public @ResponseBody IngredientType deleteIngredientType(@RequestBody dtoIngredientType deleteType) {
        Optional<IngredientType> existingType = ingredientTypeRepository.findById(deleteType.getId());

        if(!existingType.isPresent()) {
            throw new SandwichObjectNotFound("ingredient type", deleteType.getId());
        }

        LOG.info("Delete ingredient type {}", deleteType);

        ingredientTypeRepository.delete(existingType.get());

        return existingType.get();
    }
}
