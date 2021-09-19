package ru.schultetabledima.schultetable.advice;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import ru.schultetabledima.schultetable.MyApplication;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

public class AdviceModel implements AdviceContract.Model {

    private String largeTextString;

    public AdviceModel() {
//        main();
    }

    private void main() {
//        largeTextString = getStringFromRawRes(R.raw.advice1);
    }

    public String getAdvice(int idResource) {
        largeTextString = getStringFromRawRes(idResource);
        return largeTextString;
    }

    @Nullable
    private String getStringFromRawRes(int rawRes) {
        InputStream inputStream;
        try {
            inputStream = MyApplication.getContext().getResources().openRawResource(rawRes);
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
