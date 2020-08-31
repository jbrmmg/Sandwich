package com.jbr.sandwich.control;

import com.jbr.sandwich.data.*;
import com.jbr.sandwich.dataaccess.IngredientRepository;
import com.jbr.sandwich.dataaccess.UserDayRepository;
import com.jbr.sandwich.dataaccess.UserRepository;
import com.jbr.sandwich.exception.SandwichObjectNotFound;
import com.jbr.sandwich.schedule.RefreshCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SandwichController {
    final static private Logger LOG = LoggerFactory.getLogger(SandwichController.class);

    private final UserRepository userRepository;
    private final UserDayRepository userDayRepository;
    private final IngredientRepository ingredientRepository;
    private final RefreshCtrl refreshCtrl;

    @Autowired
    public SandwichController(UserRepository userRepository,
                              UserDayRepository userDayRepository,
                              IngredientRepository ingredientRepository,
                              RefreshCtrl refreshCtrl) {
        this.userRepository = userRepository;
        this.userDayRepository = userDayRepository;
        this.ingredientRepository = ingredientRepository;
        this.refreshCtrl = refreshCtrl;
    }

    @PutMapping("/sandwich")
    public void updateSandwich(@RequestParam String user,
                               @RequestParam String day,
                               @RequestParam int date,
                               @RequestBody List<dtoIngredient> ingredients) {
        LOG.info("Save sandwich - {} {} {}", user, day, date);

        // Load the day specified.
        UserDayId userDayId = new UserDayId();
        userDayId.setUser(user);
        userDayId.setDay(day);
        userDayId.setDate(date);
        Optional<UserDay> loadedDay = userDayRepository.findById(userDayId);

        if(!loadedDay.isPresent()) {
            throw new SandwichObjectNotFound("user day", userDayId.toString());
        }

        // Update the ingredients.
        loadedDay.get().getSandwich().clear();

        for(dtoIngredient nextIngredient: ingredients) {
            Optional<Ingredient> loadedIngredient = ingredientRepository.findById(nextIngredient.getId());

            loadedIngredient.ifPresent(ingredient -> loadedDay.get().getSandwich().add(ingredient));
        }

        LOG.info("Update Sandwich {}", loadedDay.get());

        userDayRepository.save(loadedDay.get());
    }

    @PostMapping("/sandwich/refresh")
    public void refreshSandwiches() {
        LOG.info("Refresh Sandwiches");
        refreshCtrl.scheduleRefresh();
    }

    private void addIngredient(Ingredient ingredient, List<dtoIngredient> ingredients) {
        for(dtoIngredient nextResultIngredient: ingredients) {
            if(nextResultIngredient.getId().equals(ingredient.getId())) {
                nextResultIngredient.incrementCount();
                return;
            }
        }

        dtoIngredient newResult = new dtoIngredient();
        newResult.setCount(1);
        newResult.setId(ingredient.getId());
        newResult.setName(ingredient.getName());

        ingredients.add(newResult);
        LOG.info("Add ingredient {}", newResult);
    }

    private void checkIngredients(UserDay userDay, List<dtoIngredient> ingredients) {
        if(userDay.getLocked()) {
            return;
        }

        for(Ingredient nextIngredient: userDay.getSandwich()) {
            addIngredient(nextIngredient, ingredients);
        }
    }

    @GetMapping("requiredingredients")
    public @ResponseBody List<dtoIngredient> getRequiredIngredients() {
        List<dtoIngredient> result = new ArrayList<>();

        LOG.info("Get required ingredients.");

        for(User nextUser: this.userRepository.findAll()) {
            for(UserDay nextUserDay: nextUser.getDays()) {
                checkIngredients(nextUserDay,result);
            }
        }

        return result;
    }
}
