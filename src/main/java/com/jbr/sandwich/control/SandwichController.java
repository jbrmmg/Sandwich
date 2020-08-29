package com.jbr.sandwich.control;

import com.jbr.sandwich.data.Ingredient;
import com.jbr.sandwich.data.UserDay;
import com.jbr.sandwich.data.UserDayId;
import com.jbr.sandwich.data.dtoIngredient;
import com.jbr.sandwich.dataaccess.IngredientRepository;
import com.jbr.sandwich.dataaccess.UserDayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SandwichController {
    final static private Logger LOG = LoggerFactory.getLogger(SandwichController.class);

    private final UserDayRepository userDayRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public SandwichController(UserDayRepository userDayepository,
                              IngredientRepository ingredientRepository) {
        this.userDayRepository = userDayepository;
        this.ingredientRepository = ingredientRepository;
    }

    @PutMapping("/sandwich")
    public void updateSandwich(@RequestParam String user,
                               @RequestParam String day,
                               @RequestParam int date,
                               @RequestBody List<dtoIngredient> ingredients) throws Exception {
        LOG.info("Save sandwich");

        // Load the day specified.
        UserDayId userDayId = new UserDayId();
        userDayId.setUser(user);
        userDayId.setDay(day);
        userDayId.setDate(date);
        Optional<UserDay> loadedDay = userDayRepository.findById(userDayId);

        if(!loadedDay.isPresent()) {
            throw new Exception("Cannot find the day specified.");
        }

        // Update the ingredients.
        loadedDay.get().getSandwich().clear();

        for(dtoIngredient nextIngredient: ingredients) {
            Optional<Ingredient> loadedIngredient = ingredientRepository.findById(nextIngredient.getId());

            if(loadedIngredient.isPresent()) {
                loadedDay.get().getSandwich().add(loadedIngredient.get());
            }
        }

        userDayRepository.save(loadedDay.get());
    }
}
