package com.finalproject.frosch.utils.convertor.valuteconvertor;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ValCursInterface {
    @GET("XML_daily.asp")
    Call<ValCurs> getValCurs();

}
