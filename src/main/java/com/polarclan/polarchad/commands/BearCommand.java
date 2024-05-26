package com.polarclan.polarchad.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import reactor.core.publisher.Mono;

public class BearCommand implements SlashCommand {

    @Override
    public String getName() {
        return "bear";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        Long width = event.getOption("width")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asLong)
                .orElse(300L);

        Long height = event.getOption("height")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asLong)
                .orElse(200L);

        return event.reply("https://placebear.com/" + width + "/" + height);

    }
}
