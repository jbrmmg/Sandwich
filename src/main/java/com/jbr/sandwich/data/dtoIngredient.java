package com.jbr.sandwich.data;

public class dtoIngredient {
    private Long id;
    private String name;
    private dtoIngredientType type;
    private Integer count;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void incrementCount() { this.count++; }
}
