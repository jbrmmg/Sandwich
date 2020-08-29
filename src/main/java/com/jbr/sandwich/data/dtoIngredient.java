package com.jbr.sandwich.data;

public class dtoIngredient {
    private Long id;
    private String name;
    private dtoIngredientType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public dtoIngredientType getType() {
        return type;
    }

    public void setType(dtoIngredientType type) {
        this.type = type;
    }
}
