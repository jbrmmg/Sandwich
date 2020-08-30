package com.jbr.sandwich.control;

import com.jbr.sandwich.data.*;
import com.jbr.sandwich.dataaccess.IngredientRepository;
import com.jbr.sandwich.dataaccess.UserDayRepository;
import com.jbr.sandwich.schedule.RefreshCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SandwichController {
    final static private Logger LOG = LoggerFactory.getLogger(SandwichController.class);

    private final UserDayRepository userDayRepository;
    private final IngredientRepository ingredientRepository;
    private final RefreshCtrl refreshCtrl;

    @Autowired
    public SandwichController(UserDayRepository userDayepository,
                              IngredientRepository ingredientRepository,
                              RefreshCtrl refreshCtrl) {
        this.userDayRepository = userDayepository;
        this.ingredientRepository = ingredientRepository;
        this.refreshCtrl = refreshCtrl;
    }

    @PutMapping("/sandwich")
    public void updateSandwich(@RequestParam String user,
                               @RequestParam String day,
                               @RequestParam int date,
                               @RequestBody List<dtoIngredient> ingredients) throws Exception {
        LOG.info("Save sandwich - {} {} {}", user, day, date);

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

            loadedIngredient.ifPresent(ingredient -> loadedDay.get().getSandwich().add(ingredient));
        }

        userDayRepository.save(loadedDay.get());
    }

    @PostMapping("/sandwich/refresh")
    public void refreshSandwichs() {
        refreshCtrl.scheduleRefresh();
    }
}
