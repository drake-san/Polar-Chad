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

    private static final String factOfTheDay = "";

    @Override
    public String getName() {
        return "fact";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uselessfacts.jsph.pl")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FactService factService = retrofit.create(FactService.class);

//        try {
//            factOfTheDay = factService.getFact().execute().body().getText();
//        } catch (IOException exception) {
//            System.err.println(exception.getMessage());
//        }
//
//        return event.deferReply().then(event.createFollowup("**" + factOfTheDay + "**")).then();

        return event.deferReply()
                .then(Mono.fromCallable(factService::getFact)) // Wrap call in Mono
                .flatMap(call -> {
                    // Create Mono from Retrofit call execution
                    return Mono.create(emitter -> {
                        call.enqueue(new Callback<Fact>() {
                            @Override
                            public void onResponse(Call<Fact> call, Response<Fact> response) {
                                if (response.isSuccessful()) {
                                    String fact = response.body().getText();
                                    emitter.success(fact); // Emit the fact if successful
                                } else {
                                    emitter.error(new Throwable("Response unsuccessful")); // Emit an error for unsuccessful response
                                }
                            }

                            @Override
                            public void onFailure(Call<Fact> call, Throwable t) {
                                emitter.error(t); // Emit the error from the network call
                            }
                        });
                    });
                })
                .flatMap(fact -> event.editReply("**" + fact + "**").thenReturn(fact)) // Process fact and edit reply
                .onErrorResume(throwable -> {
                    // Handle errors during call or reply
                    return event.editReply("An error occurred! Try again later.").thenReturn(null);
                }).then();


    }
}
