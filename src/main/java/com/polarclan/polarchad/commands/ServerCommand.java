package com.polarclan.polarchad.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

import java.util.Locale;

public class ServerCommand implements SlashCommand {
    @Override
    public String getName() {
        return "server";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return event.getInteraction().getGuild()
                .flatMap(guild -> Mono.just(
                        "This server is " + guild.getName().toUpperCase(Locale.ENGLISH) + ". " +
                                "It was created by " + guild.getOwner().block().getMention() + " and count actually " + guild.getMemberCount() + " members"
                ))
                .flatMap(event::reply);
    }
}
