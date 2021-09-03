package ru.schultetabledima.schultetable.advice;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import ru.schultetabledima.schultetable.R;

public class AdviceModel implements Serializable{

    private Context context;
    private String largeTextString;

    public AdviceModel(Context context) {
        this.context = context;
        main();
    }

    private void main() {
        largeTextString = getStringFromRawRes(R.raw.advice);
    }

    public String getAdvice(){
        return largeTextString;
    }

    @Nullable
    private String getStringFromRawRes(int rawRes) {
        InputStream inputStream;
        try {
            inputStream = context.getResources().openRawResource(rawRes);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String resultString;
        try {
            resultString = byteArrayOutputStream.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return resultString;
    }

    public void detachView(){
        context = null;
    }

    public void attachView(Context context){
        this.context = context;
    }

}
