package net.artemisia.dev.events;

import net.artemisia.dev.api.event.PlayerFishingEvent;
import net.artemisia.dev.api.math.Vec3Math;
import net.artemisia.dev.api.utils.AttributeUtils;
import net.artemisia.dev.api.utils.PlayerUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerFishing {
    @SubscribeEvent
    public void fishing(PlayerFishingEvent event){
        try {
            Player player = event.getEntity();
            FishingHook hook = event.getHook();
            Vec3Math math = new Vec3Math(player.getX(), player.getY(), player.getZ());
            double length = math.getDistance(new Vec3Math(hook.getX(),hook.getY(),hook.getZ()));
            AttributeUtils utils = new AttributeUtils(player);
            if (length > utils.getLineLength()){
                try {
                    new PlayerUtils((ServerPlayer) player).sendActionBar(Component.translatable("message.line.too.long"));
                }catch (Exception ignored){}
                event.setCanceled(true);
            }
        }catch (Exception ignored){

        }


    }
}
