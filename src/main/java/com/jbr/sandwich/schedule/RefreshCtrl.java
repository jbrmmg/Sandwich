package com.jbr.sandwich.schedule;

import com.jbr.sandwich.config.ApplicationProperties;
import com.jbr.sandwich.data.Ingredient;
import com.jbr.sandwich.data.User;
import com.jbr.sandwich.data.UserDay;
import com.jbr.sandwich.dataaccess.UserDayRepository;
import com.jbr.sandwich.dataaccess.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Component
public class RefreshCtrl {
    private static final Logger LOG = LoggerFactory.getLogger(RefreshCtrl.class);

    private final ApplicationProperties applicationProperties;
    private final UserRepository userRepository;
    private final UserDayRepository userDayRepository;

    public RefreshCtrl(ApplicationProperties applicationProperties,
                       UserRepository userRepository,
                       UserDayRepository userDayRepository) {
        this.applicationProperties = applicationProperties;
        this.userRepository = userRepository;
        this.userDayRepository = userDayRepository;
    }

    private void refreshUser(User user) {
        LOG.info("Refresh {}", user.getName());

        // For a two week period there are sandwiches to be made (locked) and to be ordered.
        // Start from either the next monday (if sat or sun) or the previous monday.
        Calendar calendar = Calendar.getInstance();

        int movement = -1;
        if ( (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) ||
                (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ) {
            movement = 1;
        }

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, movement);
        }

        List<UserDay> newDays = new ArrayList<>();
        for(int week = 0; week < 2; week++) {
            for (int day = 0; day < 5; day++) {
                LOG.info("Create {} {}", week, getDayOfWeek(calendar));

                UserDay nextNewDay = new UserDay();
                nextNewDay.setLocked(week == 0);
                nextNewDay.getId().setDate(calendar.get(Calendar.DAY_OF_MONTH));
                nextNewDay.getId().setDay(getDayOfWeek(calendar));
                nextNewDay.setMonth(getMonth(calendar));
                nextNewDay.getId().setUser(user.getId());

                // If this day already exists, the get the ingredients.
                Optional<UserDay> existing = userDayRepository.findById(nextNewDay.getId());
                if(existing.isPresent()) {
                    for(Ingredient nextIngrient: existing.get().getSandwich()) {
                        LOG.info("Update ingredients.");
                        nextNewDay.getSandwich().add(nextIngrient);
                    }
                }

                newDays.add(nextNewDay);
                calendar.add(Calendar.DATE,+1);
            }

            calendar.add(Calendar.DATE, +2);
            LOG.info("done");
        }

        // Delete the existing days.
        for(UserDay nextDay: user.getDays()) {
            userDayRepository.delete(nextDay);
        }

        for(UserDay nextDay: newDays) {
            userDayRepository.save(nextDay);
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

    @Scheduled(cron = "#{@applicationProperties.schedule}")
    public void scheduleRefresh() {
        LOG.info("Refresh sandwichs");

        for(User nextUser: userRepository.findAll()) {
            refreshUser(nextUser);
        }
    }
}
