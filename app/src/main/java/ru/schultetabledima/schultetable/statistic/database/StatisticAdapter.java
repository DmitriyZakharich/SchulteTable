package ru.schultetabledima.schultetable.statistic.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.CorrectionTime;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private final List<Result> results;

    public StatisticAdapter(Context context, List<Result> results) {
        this.results = results;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.tableSize.setText(result.getSizeField());
        holder.timeResult.setText(result.getTime());

        String newDate = CorrectionTime.getTime(result.getDate());
        holder.timeResult.setText(newDate);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView date, tableSize, timeResult;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textViewDate);
            tableSize = itemView.findViewById(R.id.textViewSize);
            timeResult = itemView.findViewById(R.id.textViewTime);
        }
    }
}
