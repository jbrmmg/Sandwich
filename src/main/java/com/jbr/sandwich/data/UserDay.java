package com.jbr.sandwich.data;

import javax.persistence.*;
import java.util.List;

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

    public String getDay() { return this.id.getDay(); }

    public int getDate() { return this.id.getDate(); }

    public String getUser() { return this.id.getUser(); }

    public String getMonth() { return this.month; }

    public List<Ingredient> getSandwich() { return this.sandwich; }
}