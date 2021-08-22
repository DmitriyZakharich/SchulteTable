package ru.schultetabledima.schultetable.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.R;

public class StatisticAdapter extends CursorRecyclerAdapter implements Serializable {
    private LayoutInflater inflater ;

    public StatisticAdapter(Cursor cursor, Context context) {
        super(cursor);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public StatisticAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new StatisticAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolderCursor(RecyclerView.ViewHolder holder, Cursor cursor) {
        ((ViewHolder)holder).textViewSize.setText(cursor.getString(cursor.getColumnIndex("size_field")));
        ((ViewHolder)holder).textViewTime.setText(cursor.getString(cursor.getColumnIndex("time")));

        String newDate = TimeAdjustment.getTime(cursor.getString(cursor.getColumnIndex("date")));

        ((ViewHolder)holder).textViewDate.setText(newDate);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements Serializable{
        TextView textViewDate, textViewSize, textViewTime;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewSize = (TextView) itemView.findViewById(R.id.textViewSize);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }

    private static class TimeAdjustment implements Serializable{

        public static String getTime(String timeDataBase){
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
            String currentDateText = dateFormat.format(currentDate);

            String dayTimeDataBase = timeDataBase.substring(6);
            String dayCurrentDateText = currentDateText.substring(6);

            if (dayTimeDataBase.equals(dayCurrentDateText)){
                return timeDataBase.substring(0 , 5);
            } else
                return dayTimeDataBase;
        }
    }
}
