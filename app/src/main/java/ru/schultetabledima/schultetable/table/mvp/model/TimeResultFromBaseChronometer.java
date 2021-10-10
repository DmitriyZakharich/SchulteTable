package ru.schultetabledima.schultetable.table.mvp.model;

public class TimeResultFromBaseChronometer {

    public static String getTime(long time) {
        long totalSecs = time / 1000;

        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        StringBuilder buildTime = new StringBuilder();

        if (hours > 0) {
            if (hours < 10)
                buildTime.append("0");

            buildTime.append(hours).append(":");

        }
        if (minutes < 10)
            buildTime.append("0");

        buildTime.append(minutes).append(":");

        if (seconds < 10)
            buildTime.append("0");

        buildTime.append(seconds);

        return  buildTime.toString();
    }

}
