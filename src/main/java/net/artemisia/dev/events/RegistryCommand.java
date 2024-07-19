package net.artemisia.dev.events;

import net.artemisia.dev.api.FisherCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class RegistryCommand {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {

        FisherCommand.register(event.getDispatcher());
    }

}
