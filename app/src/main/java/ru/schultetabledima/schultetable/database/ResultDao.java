package ru.schultetabledima.schultetable.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface ResultDao {

    @RawQuery()
    List<Result> getAll(SupportSQLiteQuery query);


    @Query("SELECT DISTINCT size_field FROM results")
    List<String> getTableSize();

    @Insert
    void insert(Result employee);

    @Update
    void update(Result employee);

    @Delete
    void delete(Result employee);

}
