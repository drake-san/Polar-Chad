package com.polarclan.polarchad.services;

import com.polarclan.polarchad.data.Fact;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FactService {
    @GET("api/v2/facts/today")
    Call<Fact> getFact();
}
