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

    private SelectionType selectionId;

    @Column(name="`order`")
    private int order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SelectionType getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(SelectionType selectionId) {
        this.selectionId = selectionId;
    }

    public String getSelection() {
        switch(this.selectionId) {
            case ONE:
                return "ONE";
            case YES_OR_NO:
                return "YES_OR_NO";
        }

        return "MANY";
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return String.format("[Type: %s %s %d]", id, selectionId.name(), order);
    }
}
