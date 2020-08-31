package com.jbr.sandwich.dataaccess;

import com.jbr.sandwich.data.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    List<Ingredient> findByName(String name);
}
