package com.polarclan.polarchad.services;

import com.polarclan.polarchad.data.waifu.Waifu;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WaifuService {
    @GET("/search")
    Call<Waifu> getWaifu(@Query("is_nsfw") Boolean isNsfw);
}
