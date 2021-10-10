package ru.schultetabledima.schultetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.schultetabledima.schultetable.advice.AdviceActivity;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.table.mvp.view.TableActivity;

public class NavigationFragment extends Fragment implements View.OnClickListener {


    public NavigationFragment() {
    }

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation, container, false);

        rootView.findViewById(R.id.tableNavigationFragment).setOnClickListener(this);
        rootView.findViewById(R.id.statisticNavigationFragment).setOnClickListener(this);
        rootView.findViewById(R.id.settingsNavigationFragment).setOnClickListener(this);
        rootView.findViewById(R.id.adviceNavigationFragment).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.tableNavigationFragment) {
            startActivity(new Intent(getActivity(), TableActivity.class));


        } else if (id == R.id.statisticNavigationFragment) {
            if (getActivity().getClass() == StatisticsActivity.class)
                return;

            startActivity(new Intent(getActivity(), StatisticsActivity.class));


        } else if (id == R.id.settingsNavigationFragment) {
            if (getActivity().getClass() == SettingsActivity.class)
                return;

            startActivity(new Intent(getActivity(), SettingsActivity.class));


        } else if (id == R.id.adviceNavigationFragment) {
            if (getActivity().getClass() == AdviceActivity.class)
                return;

            startActivity(new Intent(getActivity(), AdviceActivity.class));
        }
    }
}