package com.finalproject.frosch.utils.convertor.valuteconvertor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValuteConvector {
    ValCursInterface retrofitInterface;
    Activity activity;
    SharedPreferences preferences;

    public ValuteConvector(Activity activity) {
        this.activity = activity;
        retrofitInterface = ValCursClient.getClient().create(ValCursInterface.class);
        preferences = this.activity.getPreferences(Context.MODE_PRIVATE);
    }

    public void getValCurs(){
        Call<ValCurs> valCursCall = retrofitInterface.getValCurs();
        valCursCall.enqueue(new Callback<ValCurs>() {
            @Override
            public void onResponse(@NonNull Call<ValCurs> call, @NonNull Response<ValCurs> response) {
                ValCurs valCurs = response.body();
                ValCurs.Valute valute = valCurs.getValuteById("R01235");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Rub", "1");
                editor.putString("Dollar", valute.getValue());
                valute = valCurs.getValuteById("R01239");
                editor.putString("Euro", valute.getValue());
            }

            @Override
            public void onFailure(@NonNull Call<ValCurs> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Не удаеться выполнить перевод. Повторите позже",
                        Toast.LENGTH_SHORT).show();

                call.cancel();
            }
        });

        for(String key: preferences.getAll().keySet()) {
            Log.e("Preference", key + " - " + preferences.getAll().get(key));
        }
        Log.d("Curs", preferences.getString("Rub", ""));
    }

    public Float convertFromRub(Float rubVal, int idVal){
        Float val = rubVal;
        if(idVal == 1) val /= Float.parseFloat(preferences.getString("Dollar", "1"));
        else if(idVal == 2) val /= Float.parseFloat(preferences.getString("Euro", "1"));
        return val;
    }

    public Float convertToRub(Float rubVal, int idVal){
        Float val = rubVal;
        if(idVal == 1) val *= Float.parseFloat(preferences.getString("Dollar", "1"));
        else if(idVal == 2) val *= Float.parseFloat(preferences.getString("Euro", "1"));
        return val;
    }
}
