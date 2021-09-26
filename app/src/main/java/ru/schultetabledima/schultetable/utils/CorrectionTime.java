package ru.schultetabledima.schultetable.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CorrectionTime {

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
