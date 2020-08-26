package com.jbr.sandwich.dataaccess;

import com.jbr.sandwich.data.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
}
