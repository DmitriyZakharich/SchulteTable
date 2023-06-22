package ru.schultetabledima.schultetable.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
class Result(
    val date: String,
    @ColumnInfo(name = "size_field")
    val sizeField: String,
    val time: String,
    @ColumnInfo(name = "quantity_tables")
    val quantityTables: Int,
    @ColumnInfo(name = "value_type")
    val valueType: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "Result{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", sizeField='" + sizeField + '\'' +
                ", quantityTables=" + quantityTables +
                ", valueType='" + valueType + '\'' +
                '}'
    }
}