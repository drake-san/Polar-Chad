package com.polarclan.polarchad.commands;

import com.polarclan.polarchad.data.waifu.Waifu;
import com.polarclan.polarchad.services.WaifuService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WaifuCommand implements SlashCommand {

    @Override
    public String getName() {
        return "waifu";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.waifu.im")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WaifuService waifuService = retrofit.create(WaifuService.class);

        boolean isNsfw;

        if (event.getOption("nsfw").isPresent())
            isNsfw = event.getOption("nsfw")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asBoolean)
                    .get();
        else {
            isNsfw = false;
        }

        return event.deferReply()
                .then(Mono.fromCallable(() -> waifuService.getWaifu(isNsfw))
                        .flatMap(call -> Mono.create(emitter -> {
                            call.enqueue(new Callback<Waifu>() {
                                @Override
                                public void onResponse(Call<Waifu> call, Response<Waifu> response) {
                                    if (response.isSuccessful()) {
                                        String waifuImageUrl = response.body().getImages().getFirst().getUrl();
                                        emitter.success(waifuImageUrl);
                                    } else {
                                        emitter.error(new Throwable("Response unsuccessful"));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Waifu> call, Throwable t) {
                                    emitter.error(t);
                                }
                            });
                        }))
                        .flatMap(image -> event.editReply("" + image).thenReturn(image))
                        .onErrorResume(throwable -> event.editReply("An error occurred! Try again later.").thenReturn(null)).then());

    }
}
