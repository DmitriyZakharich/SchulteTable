package ru.schultetabledima.schultetable;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidessence.recyclerviewcursoradapter.RecyclerViewCursorAdapter;
import com.androidessence.recyclerviewcursoradapter.RecyclerViewCursorViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class StatisticsAdapter extends RecyclerViewCursorAdapter<StatisticsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    Cursor cursor;

    public StatisticsAdapter(Context context) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.cursor = cursor;


        setupCursorAdapter(null, 0, R.layout.list_item, false);

    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull StatisticsAdapter.ViewHolder holder, int position) {
        mCursorAdapter.getCursor().moveToPosition(position);
        setViewHolder(holder);
        mCursorAdapter.bindView(null, mContext, mCursorAdapter.getCursor());




//        holder.textViewDate.setText(cursor.getString(cursor.getColumnIndex("date")));
//        holder.textViewSize.setText(cursor.getString(cursor.getColumnIndex("size_field")));
//        holder.textViewTime.setText(cursor.getString(cursor.getColumnIndex("time")));
//        if (cursor.moveToNext())
//        Log.d("RecyclerView","onBindViewHolder");

    }

//    @Override
//    public int getItemCount() {
//        return cursor.getCount();
//    }

    public static class ViewHolder extends RecyclerViewCursorViewHolder {
        TextView textViewDate, textViewSize, textViewTime;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            Log.d("RecyclerView","класс ViewHolder");

            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewSize = (TextView) itemView.findViewById(R.id.textViewSize);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }

        @Override
        public void bindCursor(Cursor cursor) {
            textViewDate.setText(cursor.getString(cursor.getColumnIndex("date")));
            textViewSize.setText(cursor.getString(cursor.getColumnIndex("size_field")));
            textViewTime.setText(cursor.getString(cursor.getColumnIndex("time")));
        }
    }
}
