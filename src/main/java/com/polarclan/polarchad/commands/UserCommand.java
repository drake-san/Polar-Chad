package com.polarclan.polarchad.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import java.time.ZoneId;

public class UserCommand implements SlashCommand {

    @Override
    public String getName() {
        return "user";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        String userDisplayName = event.getInteraction().getMember().get().getMention();
        String higherRole = event.getInteraction().getMember().get().getHighestRole().block().getMention();

        if (event.getOption("user").isPresent()) {

            User user = event.getOption("user").flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asUser)
                    .get()
                    .block();

            return event.reply().withContent("This is " +
                    user.getMention()
                    + ", he/she joined the server on "
                    + user.asMember(Snowflake.of(1127018342113357914L)).block().getJoinTime().get()
                    .atZone(ZoneId.systemDefault()).toLocalDate()
                    + " and his/her highest role on this server is " + user.asMember(Snowflake.of(1127018342113357914L)).block()
                    .getHighestRole()
                    .block()
                    .getMention() + ".");
        } else {
            return event.reply().withContent("You're " +
                    userDisplayName
                    + ", you joined the server on "
                    + event.getInteraction().getMember().get().getJoinTime().get()
                    .atZone(ZoneId.systemDefault()).toLocalDate()
                    + " and your highest role on this server is " + higherRole + ".");
        }

    }
}
    
    

    


