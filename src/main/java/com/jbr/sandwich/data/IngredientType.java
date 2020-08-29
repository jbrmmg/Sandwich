package com.jbr.sandwich.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class IngredientType {
    public enum SelectionType { ONE, YES_OR_NO, MANY }

    @Id
    private String id;

    private int selectionId;

    @Column(name="`order`")
    private int order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }

    public String getSelection() {
        switch(this.selectionId) {
            case 0:
                return "one";
            case 1:
                return "yesno";
        }

        return "many";
    }

    public void setSelection(SelectionType selection) {
        switch (selection) {
            case ONE:
                this.selectionId = 0;
                break;
            case YES_OR_NO:
                this.selectionId = 1;
                break;
            case MANY:
                this.selectionId = 2;
                break;
        }
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
