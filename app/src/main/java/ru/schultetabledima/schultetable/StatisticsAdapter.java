package ru.schultetabledima.schultetable;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    Cursor cursor;
    List<Results> resultsList = new ArrayList<>();

    public StatisticsAdapter(Context context, Cursor cursor) {
        this.inflater = LayoutInflater.from(context);
        this.cursor = cursor;
        cursorToResultsList();
    }

    void cursorToResultsList(){

        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                Results results = new Results();
                results.date = cursor.getString(cursor.getColumnIndex("date"));
                results.sizeField = cursor.getString(cursor.getColumnIndex("size_field"));
                results.time = cursor.getString(cursor.getColumnIndex("time"));
                resultsList.add(results);
            }
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StatisticsAdapter.ViewHolder holder, int position) {
        Results results = resultsList.get(position);
        holder.textViewDate.setText(results.date);
        holder.textViewSize.setText(results.sizeField);
        holder.textViewTime.setText(results.time);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewSize, textViewTime;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewSize = (TextView) itemView.findViewById(R.id.textViewSize);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }


    private class Results{
        private String date;
        private String sizeField;
        private String time;
    }
}
