package com.finalproject.frosch.utils.convertor.valuteconvertor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ValCursClient {
    public static Retrofit getClient(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new Retrofit.Builder()
                .baseUrl("http://www.cbr.ru/scripts/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();
    }
}
