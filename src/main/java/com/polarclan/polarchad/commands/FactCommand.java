package com.polarclan.polarchad.commands;

import com.polarclan.polarchad.data.Fact;
import com.polarclan.polarchad.services.FactService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FactCommand implements SlashCommand {

    private static String factOfTheDay = "";

    public static void getFactOfTheDay() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uselessfacts.jsph.pl")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FactService factService = retrofit.create(FactService.class);


        factService.getFact().enqueue(new Callback<Fact>() {
            @Override
            public void onResponse(Call<Fact> call, Response<Fact> response) {
                factOfTheDay = response.body().getText();
            }

            @Override
            public void onFailure(Call<Fact> call, Throwable t) {
                System.err.println(t.getMessage());
            }
        });
    }


    @Override
    public String getName() {
        return "fact";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        event.reply("Here's the useless fact of the day").subscribe();

        return event.createFollowup("**" + factOfTheDay + "**").then();

    }
}
