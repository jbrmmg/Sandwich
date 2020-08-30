package com.jbr.sandwich.data;

public class dtoIngredientType {
    private String id;
    private int order;
    private IngredientType.SelectionType selection;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public IngredientType.SelectionType getSelection() {
        return selection;
    }

    public void setSelection(IngredientType.SelectionType selection) {
        this.selection = selection;
    }
}
