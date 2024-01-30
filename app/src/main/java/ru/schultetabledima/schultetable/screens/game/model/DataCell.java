package ru.schultetabledima.schultetable.screens.game.model;

public class DataCell {
    private final int id;
    private final int value;

    private int typeAnimation = -1;

    public DataCell(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setTypeAnimation(int typeAnimation) {
        this.typeAnimation = typeAnimation;
    }

    public int getTypeAnimation() {
        return typeAnimation;
    }
}