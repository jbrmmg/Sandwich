package com.jbr.sandwich.data;

import jakarta.persistence.*;
import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name="user")
public class User {
    @Id
    private String id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "id.user")
    private List<UserDay> days;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserDay> getDays() {
        return days;
    }

    public void setDays(List<UserDay> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return String.format(
                "[User: %s %s %s [%s]]",
                id,
                name,
                email,
                String.join(",", days.toString()) );
    }
}
