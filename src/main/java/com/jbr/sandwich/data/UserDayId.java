package com.jbr.sandwich.data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserDayId implements Serializable {
    private String day;
    private int date;
    private String user;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("[UserDayId: %s %d %s]", day, date, user);
    }
}
