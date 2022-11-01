package ru.schultetabledima.schultetable.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.CorrectionTime;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {

    public interface OptionsMenuLongClickListener {
        void onOptionsMenuClicked(Result result, View v, int position);
    }

    private LayoutInflater inflater;
    private final List<Result> results;
    private final OptionsMenuLongClickListener onLongClickListener;

    public StatisticAdapter(Context context, List<Result> results, OptionsMenuLongClickListener onLongClickListener) {
        this.results = results;
        this.inflater = LayoutInflater.from(context);
        this.onLongClickListener = onLongClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public StatisticAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new StatisticAdapter.ViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Result result = results.get(position);

        holder.tableSize.setText(result.getSizeField());
        holder.timeResult.setText(result.getTime());

        String newDate = CorrectionTime.getTime(result.getDate());
        holder.date.setText(newDate);

        holder.tableSize.setOnLongClickListener(v -> {
            onLongClickListener.onOptionsMenuClicked(result, v, position);
            return false;
        });
        holder.timeResult.setOnLongClickListener(v -> {
            onLongClickListener.onOptionsMenuClicked(result, v, position);
            return false;
        });

        holder.date.setOnLongClickListener(v -> {
            onLongClickListener.onOptionsMenuClicked(result, v, position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, tableSize, timeResult;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textViewDate);
            tableSize = itemView.findViewById(R.id.textViewSize);
            timeResult = itemView.findViewById(R.id.textViewTime);
        }
    }
}
