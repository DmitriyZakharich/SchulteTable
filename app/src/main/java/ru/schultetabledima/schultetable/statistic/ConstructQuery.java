package ru.schultetabledima.schultetable.statistic;

import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.ArrayList;
import java.util.List;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;

public class ConstructQuery {

    public static SimpleSQLiteQuery getQuery(int quantityTables, String valueType, String playedSizes){
        StringBuilder buildQuery = new StringBuilder();
        List<Object> args = new ArrayList<>();

        boolean insertAlready = false;

        buildQuery.append("SELECT * FROM RESULTS");

        if (quantityTables == 1 || quantityTables == 2) {
            buildQuery.append(" WHERE");
            buildQuery.append(" quantity_tables = ?");
            args.add(quantityTables);
            insertAlready = true;
        }

        if (!valueType.isEmpty()) {

            if (insertAlready) {
                buildQuery.append(" AND");
            } else {
                buildQuery.append(" WHERE");
                insertAlready = true;
            }

            buildQuery.append(" value_type = ?");
            args.add(valueType);
        }

        if (!playedSizes.equals(App.getContext().getString(R.string.allSize))) {
            if (insertAlready) {
                buildQuery.append(" AND");
            } else {
                buildQuery.append(" WHERE");
                insertAlready = true;
            }

            buildQuery.append(" size_field = ?");
            args.add(playedSizes);
        }
        buildQuery.append(";");

        return new SimpleSQLiteQuery(buildQuery.toString(), args.toArray());
    }
}
