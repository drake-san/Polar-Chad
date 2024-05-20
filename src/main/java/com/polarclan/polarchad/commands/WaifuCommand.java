package com.polarclan.polarchad.commands;

import com.polarclan.polarchad.services.WaifuService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import reactor.core.publisher.Mono;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class WaifuCommand implements SlashCommand {

    private static String waifuImageUrl = "";

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

        boolean isNsfw = false;

        if (event.getOption("nsfw").isPresent())
            isNsfw = event.getOption("nsfw")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asBoolean)
                    .get();

        try {
            waifuImageUrl = waifuService.getWaifu(isNsfw)
                    .execute()
                    .body()
                    .getImages()
                    .getFirst()
                    .getUrl();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }

        return event.deferReply().then(event.createFollowup(waifuImageUrl)).then();

    }
}
