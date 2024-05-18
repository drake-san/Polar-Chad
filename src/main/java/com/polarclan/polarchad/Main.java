package com.polarclan.polarchad;

import com.polarclan.polarchad.commands.FactCommand;
import com.polarclan.polarchad.listener.GlobalSlashCommandListener;
import com.polarclan.polarchad.listener.GuildSlashCommandListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.gateway.intent.IntentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {

        FactCommand.getFactOfTheDay();

        //Creates the gateway client and connects to the gateway
        final GatewayDiscordClient client = DiscordClientBuilder.create(System.getenv("BOT_TOKEN"))
                .build()
                .gateway()
                .setEnabledIntents(IntentSet.all())
                .login()
                .block();


        ClientActivity botActivity = ClientActivity.streaming("Lofi", "https://www.youtube.com/watch?v=jfKfPfyJRdk&pp=ygUEbG9maQ%3D%3D");
        ClientPresence botPresence = ClientPresence.online(botActivity);

        client.updatePresence(botPresence).subscribe();

        List<String> guildCommands = List.of("greet.json", "sus.json", "user.json", "server.json");
        List<String> globalCommands = List.of("ping.json", "joke.json", "fact.json");

        try {
            new GlobalCommandRegistrar(client.getRestClient()).registerCommands(globalCommands);
        } catch (Exception e) {
            LOGGER.error("Error trying to register global slash commands", e);
        }

        try {
            new GuildCommandRegistrar(client.getRestClient()).registerCommands(guildCommands);
        } catch (Exception e) {
            LOGGER.error("Error trying to register guild slash commands", e);
        }

        client.on(ChatInputInteractionEvent.class, GlobalSlashCommandListener::handle)
                .then(client.onDisconnect())
                .subscribe();

        client.on(ChatInputInteractionEvent.class, GuildSlashCommandListener::handle)
                .then(client.onDisconnect())
                .subscribe();

    }
}
