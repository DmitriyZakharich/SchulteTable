package ru.schultetabledima.schultetable.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "results")
public class Result {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String date;
    private String time;

    @ColumnInfo(name = "size_field")
    private String sizeField;

    @ColumnInfo(name = "quantity_tables")
    private int quantityTables;

    @ColumnInfo(name = "value_type")
    private String valueType;

    public Result(String date, String sizeField, String time, int quantityTables, String valueType) {
        this.date = date;
        this.sizeField = sizeField;
        this.time = time;
        this.quantityTables = quantityTables;
        this.valueType = valueType;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSizeField() {
        return sizeField;
    }

    public int getQuantityTables() {
        return quantityTables;
    }

    public String getValueType() {
        return valueType;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", sizeField='" + sizeField + '\'' +
                ", quantityTables=" + quantityTables +
                ", valueType='" + valueType + '\'' +
                '}';
    }
}
