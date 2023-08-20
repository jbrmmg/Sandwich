package com.jbr.sandwich.data;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"JpaDataSourceORMInspection", "FieldMayBeFinal"})
@Entity
@Table(name="user_day")
public class UserDay {
    @EmbeddedId
    private UserDayId id;

    @ManyToMany
    @JoinTable(
            name = "user_day_ingredient",
            joinColumns = {
                    @JoinColumn(name="date"),
                    @JoinColumn(name="day"),
                    @JoinColumn(name="user")
            },
            inverseJoinColumns = @JoinColumn(name="ingredient")
    )
    private List<Ingredient> sandwich;

    private String month;

    private Boolean locked;

    public UserDay() {
        sandwich = new ArrayList<>();
        id = new UserDayId();
    }

    public String getDay() { return this.id.getDay(); }

    public int getDate() { return this.id.getDate(); }

    public String getUser() { return this.id.getUser(); }

    public String getMonth() { return this.month; }

    public List<Ingredient> getSandwich() { return this.sandwich; }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public UserDayId getId() { return this.id; }

    public void setMonth(String month) {this.month = month;}

    @Override
    public String toString() {
        return String.format(
                "[User: %s %s %s [%s]]",
                id,
                month,
                locked,
                String.join(",", sandwich.toString()) );
    }
}
