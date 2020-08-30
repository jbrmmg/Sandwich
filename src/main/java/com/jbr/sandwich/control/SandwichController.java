package com.jbr.sandwich.control;

import com.jbr.sandwich.data.*;
import com.jbr.sandwich.dataaccess.IngredientRepository;
import com.jbr.sandwich.dataaccess.UserDayRepository;
import com.jbr.sandwich.dataaccess.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
public class SandwichController {
    final static private Logger LOG = LoggerFactory.getLogger(SandwichController.class);

    private final UserRepository userRepository;
    private final UserDayRepository userDayRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public SandwichController(UserRepository userRepository,
                              UserDayRepository userDayepository,
                              IngredientRepository ingredientRepository) {
        this.userRepository = userRepository;
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

            loadedIngredient.ifPresent(ingredient -> loadedDay.get().getSandwich().add(ingredient));
        }

        userDayRepository.save(loadedDay.get());
    }

    private void lockUser(User user) {
        // Any day currently locked, delete.
        for(UserDay nextDay: user.getDays()) {
            if(nextDay.getLocked()) {
                userDayRepository.delete(nextDay);
            }
        }

        // Any day currently not locked, lock.
        for(UserDay nextDay: user.getDays()) {
            if(!nextDay.getLocked()) {
                nextDay.setLocked(true);
                userDayRepository.save(nextDay);
            }
        }

        // Create days for a week on monday.
        Calendar calendar = Calendar.getInstance();
        for(int i = 0; i < 2; i++) {
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DATE, 1);
            }
            calendar.add(Calendar.DATE, 1);
        }

        for(int i = 0; i < 5; i++) {
            UserDay nextNewDay = new UserDay();
            nextNewDay.setLocked(false);
            nextNewDay.getId().setDate(calendar.get(Calendar.DAY_OF_MONTH));
            nextNewDay.getId().setDay(getDayOfWeek(calendar));
            nextNewDay.setMonth(getMonth(calendar));
            nextNewDay.getId().setUser(user.getId());

            userDayRepository.save(nextNewDay);

            calendar.add(Calendar.DATE, 1);
        }
    }

    private String getDayOfWeek(Calendar calendar) {
        switch(calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
        }

        return "Sunday";
    }

    private String getMonth(Calendar calendar) {
        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                return "Jan";
            case Calendar.FEBRUARY:
                return "Feb";
            case Calendar.MARCH:
                return "Mar";
            case Calendar.APRIL:
                return "Apr";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "Jun";
            case Calendar.JULY:
                return "Jul";
            case Calendar.AUGUST:
                return "Aug";
            case Calendar.SEPTEMBER:
                return "Sep";
            case Calendar.OCTOBER:
                return "Oct";
            case Calendar.NOVEMBER:
                return "Nov";
        }

        return "Dec";
    }

    @PostMapping("/sandwich/lock")
    public void lockSandwich() {
        for(User nextUser: userRepository.findAll()) {
            lockUser(nextUser);
        }
    }
}
