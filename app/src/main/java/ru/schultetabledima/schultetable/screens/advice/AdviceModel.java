package ru.schultetabledima.schultetable.screens.advice;

import android.content.res.Resources;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

public class AdviceModel implements AdviceContract.Model {

    @Override
    public String getAdvice(int idResource) {
        return getStringFromRawRes(idResource);
    }

    @Nullable
    private String getStringFromRawRes(int rawRes) {
        InputStream inputStream;
        try {
            inputStream = App.getAppContext().getResources().openRawResource(rawRes);
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
}