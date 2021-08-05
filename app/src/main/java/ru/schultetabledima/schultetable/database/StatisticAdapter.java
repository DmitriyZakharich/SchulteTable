package ru.schultetabledima.schultetable.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.schultetabledima.schultetable.R;

public class StatisticAdapter extends CursorRecyclerAdapter {
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
        ((ViewHolder)holder).textViewDate.setText(cursor.getString(cursor.getColumnIndex("date")));
        ((ViewHolder)holder).textViewSize.setText(cursor.getString(cursor.getColumnIndex("size_field")));
        ((ViewHolder)holder).textViewTime.setText(cursor.getString(cursor.getColumnIndex("time")));
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

}
