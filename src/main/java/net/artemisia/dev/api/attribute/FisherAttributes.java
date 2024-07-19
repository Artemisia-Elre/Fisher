package net.artemisia.dev.api.attribute;

import net.artemisia.dev.api.Information;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Information.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FisherAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Information.MODID);
    public static final RegistryObject<Attribute> FISHING_POWER = ATTRIBUTES.register(
            "fishing.power",
            () ->
                    new RangedAttribute(
                            "attribute.name.fishing.power",
                            1.0D,
                                    1.0D,
                                    1024.0D
                    ).setSyncable(true)
            );
    public static final RegistryObject<Attribute> FISHING_LINE_LENGTH = ATTRIBUTES.register(
            "fishing.line.length",
            () ->
                    new RangedAttribute(
                            "attribute.name.fishing.line.length",
                            5.0D,
                            5.0D,
                            32.0D
                    ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> FISHING_RATE = ATTRIBUTES.register(
            "fishing.rate",
            () ->
                    new RangedAttribute(
                            "attribute.name.fishing.rate",
                            0.1D,
                            0.0D,
                            1.0D
                    ).setSyncable(true)
    );
    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }
    @SubscribeEvent
    public static void setAttribute(EntityAttributeModificationEvent event){
        if (!event.has(EntityType.PLAYER, FISHING_POWER.get())) {
            event.add(EntityType.PLAYER,
                    FISHING_POWER.get()
            );
        }
        if (!event.has(EntityType.PLAYER, FISHING_LINE_LENGTH.get())) {
            event.add(EntityType.PLAYER,
                    FISHING_LINE_LENGTH.get()
            );
        }
        if (!event.has(EntityType.PLAYER, FISHING_RATE.get())) {
            event.add(EntityType.PLAYER,
                    FISHING_RATE.get()
            );
        }
    }
}
