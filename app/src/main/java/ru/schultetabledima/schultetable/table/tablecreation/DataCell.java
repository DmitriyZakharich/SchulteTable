package ru.schultetabledima.schultetable.table.tablecreation;

public class DataCell {
    private final int id;
    private final int value;

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
}
