package ru.schultetabledima.schultetable.advice;

import android.content.res.Resources;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

public class AdviceModel implements AdviceContract.Model {

    private final List<Integer> adviceResource = new ArrayList<>(3);
    private final List<String> listAdvice = new ArrayList(3);

    @Inject
    public AdviceModel() {
    }

    @Override
    public List<String> getAdvice() {
        init();
        for (int id : adviceResource)
            listAdvice.add(getStringFromRawRes(id));

        return listAdvice;
    }

    private void init() {

        if (Locale.getDefault().getLanguage().equals("ru")) {
            adviceResource.add(R.raw.advice1_ru);
            adviceResource.add(R.raw.advice2_ru);
            adviceResource.add(R.raw.advice3_ru);
        } else {
            adviceResource.add(R.raw.advice1_en);
            adviceResource.add(R.raw.advice2_en);
            adviceResource.add(R.raw.advice3_en);
        }
    }


    @Nullable
    private String getStringFromRawRes(int rawRes) {
        InputStream inputStream;
        try {
            inputStream = App.getContext().getResources().openRawResource(rawRes);
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
