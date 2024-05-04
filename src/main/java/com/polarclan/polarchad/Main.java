package com.polarclan.polarchad;

import com.polarclan.polarchad.listener.SlashCommandListener;
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

        //Creates the gateway client and connects to the gateway
        final GatewayDiscordClient client = DiscordClientBuilder.create(System.getenv("BOT_TOKEN"))
                .build()
                .gateway()
                .setEnabledIntents(IntentSet.all())
                .login()
                .block();


        ClientActivity botActivity = ClientActivity.playing("Rocket League");
        ClientPresence botPresence = ClientPresence.online(botActivity);

        client.updatePresence(botPresence).subscribe();

        List<String> commands = List.of("greet.json", "ping.json", "sus.json", "user.json", "server.json", "joke.json");

//        try {
//            new GlobalCommandRegistrar(client.getRestClient()).registerCommands(commands);
//        } catch (Exception e) {
//            LOGGER.error("Error trying to register global slash commands", e);
//        }

        try {
            new GuildCommandRegistrar(client.getRestClient()).registerCommands(commands);
        } catch (Exception e) {
            LOGGER.error("Error trying to register guild slash commands", e);
        }

        //Register our slash command listener
        client.on(ChatInputInteractionEvent.class, SlashCommandListener::handle)
                .then(client.onDisconnect())
                .block(); // We use .block() as there is not another non-daemon thread and the jvm would close otherwise.
    }
}
