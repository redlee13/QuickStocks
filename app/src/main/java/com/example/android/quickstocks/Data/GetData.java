package com.example.android.quickstocks.Data;

import com.example.android.quickstocks.DetailsModel;
import com.example.android.quickstocks.MainModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetData {

    @GET("en/tadawul/tasi")
    Call<List<MainModel>> getTasi();

    @GET("{company_url}")
    Call<List<DetailsModel>> getCompanyUrl(@Path(value = "company_url", encoded = true) String companyUrl);
}
