package com.polarclan.polarchad.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import net.thauvin.erik.jokeapi.JokeApi;
import net.thauvin.erik.jokeapi.JokeConfig;
import net.thauvin.erik.jokeapi.exceptions.HttpErrorException;
import net.thauvin.erik.jokeapi.models.Joke;
import net.thauvin.erik.jokeapi.models.Language;
import reactor.core.publisher.Mono;

public class JokeCommand implements SlashCommand {
    @Override
    public String getName() {
        return "joke";
    }

    public Joke getJoke(Language language, Boolean safeJoke) {
        Joke randomJoke = null;

        try {
            JokeConfig jokeConfig = new JokeConfig.Builder().safe(safeJoke).lang(language).build();

            randomJoke = JokeApi.joke(jokeConfig);

        } catch (HttpErrorException exception) {
            System.err.println(exception.getMessage());
        }

        assert randomJoke != null;

        return randomJoke;
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        if (event.getCommandName().equals("joke") && event.getOption("language").isEmpty()) {

            Joke joke = getJoke(Language.EN, event.getOption("safe")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asBoolean).get());

            if (joke.getJoke().size() <= 1) {
                return event.reply().withContent(joke.getJoke().getFirst());
            } else {
                return event.reply().withContent(joke.getJoke().getFirst() + "\n" + joke.getJoke().getLast());
            }

        } else if (event.getCommandName().equals("joke") && event.getOption("safe").isPresent() && event.getOption("language").isPresent()) {
            if (event.getOption("language").flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asString)
                    .get().equalsIgnoreCase("fr")) {

                Joke joke = getJoke(Language.FR, event.getOption("safe").isPresent() && event.getOption("safe").flatMap(ApplicationCommandInteractionOption::getValue)
                        .map(ApplicationCommandInteractionOptionValue::asBoolean)
                        .get());

                if (joke.getJoke().size() <= 1) {
                    return event.reply().withContent(joke.getJoke().getFirst());
                } else {
                    return event.reply().withContent(joke.getJoke().getFirst() + "\n" + joke.getJoke().getLast());
                }

            } else if (event.getOption("language").flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asString)
                    .get()
                    .equalsIgnoreCase("es")) {
                Joke joke = getJoke(Language.ES, event.getOption("safe").isPresent() && event.getOption("safe").flatMap(ApplicationCommandInteractionOption::getValue).map(ApplicationCommandInteractionOptionValue::asBoolean).get());

                if (joke.getJoke().size() <= 1) {
                    return event.reply().withContent(joke.getJoke().getFirst());
                } else {
                    return event.reply().withContent(joke.getJoke().getFirst() + "\n" + joke.getJoke().getLast());
                }
            } else if (event.getOption("language").flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asString)
                    .get().equalsIgnoreCase("de")) {
                Joke joke = getJoke(Language.DE, event.getOption("safe").isPresent() && event.getOption("safe").flatMap(ApplicationCommandInteractionOption::getValue)
                        .map(ApplicationCommandInteractionOptionValue::asBoolean)
                        .get());

                if (joke.getJoke().size() <= 1) {
                    return event.reply().withContent(joke.getJoke().getFirst());
                } else {
                    return event.reply().withContent(joke.getJoke().getFirst() + "\n" + joke.getJoke().getLast());
                }
            }
        }
        return null;
    }
}
