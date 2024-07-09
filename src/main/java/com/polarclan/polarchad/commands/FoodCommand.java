package com.polarclan.polarchad.commands;

import com.polarclan.polarchad.data.Food;
import com.polarclan.polarchad.services.FoodService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodCommand implements SlashCommand {

    @Override
    public String getName() {
        return "food";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://foodish-api.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodService foodService = retrofit.create(FoodService.class);

        return event.deferReply()
                .then(Mono.fromCallable(foodService::getFood)
                        .flatMap(call -> Mono.create(emitter -> {
                            call.enqueue(new Callback<Food>() {
                                @Override
                                public void onResponse(Call<Food> call, Response<Food> response) {
                                    if (response.isSuccessful()) {
                                        String foodImageUrl = response.body().getImage();
                                        emitter.success(foodImageUrl);
                                    } else {
                                        emitter.error(new Throwable("Response unsuccessful"));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Food> call, Throwable t) {
                                    emitter.error(t);
                                }
                            });
                        }))
                        .flatMap(image -> event.editReply("" + image).thenReturn(image))
                        .onErrorResume(throwable -> event.editReply("An error occurred! Try again later.").thenReturn(null)).then());
    }
}
