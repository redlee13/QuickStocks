package com.example.android.quickstocks.Data;

import com.github.slashrootv200.retrofithtmlconverter.HtmlConverterFactory;

import retrofit2.Retrofit;

import static com.example.android.quickstocks.Data.Constants.BASE_URL;

public class RetrofitInstance {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(HtmlConverterFactory.create(BASE_URL))
                    .build();
        }
        return retrofit;
    }

}

