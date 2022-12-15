package com.example.fito.ui.food.edamamapi.nutrientModel;

public class Fats {

    private float quantity;
    private String label;

    public Fats(float quantity, String label) {
        this.quantity = quantity;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
