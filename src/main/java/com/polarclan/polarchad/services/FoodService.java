package com.polarclan.polarchad.services;

import com.polarclan.polarchad.data.Food;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodService {
    @GET("/api")
    Call<Food> getFood();
}
