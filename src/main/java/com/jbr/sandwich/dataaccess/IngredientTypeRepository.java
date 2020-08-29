package com.jbr.sandwich.dataaccess;

import com.jbr.sandwich.data.IngredientType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientTypeRepository extends JpaRepository<IngredientType,String> {
}
