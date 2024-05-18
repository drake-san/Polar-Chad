package com.polarclan.polarchad.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Mono;

public class PingCommand implements SlashCommand {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        Button pingButton = Button.primary("ping-button", ReactionEmoji.unicode("\uD83C\uDFD3"), "Pong").disabled();

        //We reply to the command with "Pong!" and make sure it is ephemeral (only the command user can see it)
        return event.reply()
                .withEphemeral(true)
                .withComponents(ActionRow.of(pingButton));
    }
}

