package com.polarclan.polarchad.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import reactor.core.publisher.Mono;

public class GreetCommand implements SlashCommand {
    @Override
    public String getName() {
        return "greet";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {


        if (event.getOption("name").isPresent()) {

            String name = event.getOption("name")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asUser)
                    .get()
                    .block()
                    .getMention();

            return event.reply()
                    .withContent("Hello, " + name);
        } else {
            return event.reply()
                    .withEphemeral(true)
                    .withContent("Hello, " + event.getInteraction().getUser().getMention());
        }
    }
}
