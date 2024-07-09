package com.polarclan.polarchad.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

import java.util.Random;

public class SusCommand implements SlashCommand {

    @Override
    public String getName() {
        return "sus";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        return event.getInteraction().getGuild()
                .flatMap(guild -> {
                    int membersCount = guild.getMemberCount();
                    int randomMemberIndex = new Random().nextInt(0, membersCount);
                    Member randomMember = guild.requestMembers().collectList().block().get(randomMemberIndex);


                    if (randomMember.getDisplayName().equalsIgnoreCase("Polar Chad"))
                        return Mono.just("Sorry to break hearts but.... I'm the impostor");

                    else if (randomMember.isBot())
                        return Mono.just(randomMember.getMention() + "? Never suspected that a bot could act sussy.");
                    else
                        return Mono.just("You're acting kinda sus " + randomMember.getMention() + ". You might be the impostor!");

                }).flatMap(event::reply);

    }
}
