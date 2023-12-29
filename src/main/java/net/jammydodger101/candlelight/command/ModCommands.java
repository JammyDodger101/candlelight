package net.jammydodger101.candlelight.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public final class ModCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            GetCandleStatusCommand.register(dispatcher);
            RevivePlayersCommand.register(dispatcher);
        });
    }
}
