package net.artemisia.dev;


import net.artemisia.dev.api.Information;
import net.artemisia.dev.api.attribute.FisherAttributes;
import net.artemisia.dev.api.configuration.FisherConfig;
import net.artemisia.dev.events.PlayerFishing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(Information.MODID)
public class Fisher {

    public Fisher(){
        FisherConfig.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new PlayerFishing());
        FisherAttributes.register(bus);

    }
}
