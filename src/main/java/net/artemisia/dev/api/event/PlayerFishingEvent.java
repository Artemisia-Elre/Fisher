package net.artemisia.dev.api.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;

public class PlayerFishingEvent extends PlayerEvent {
    private final FishingHook hook;
    public PlayerFishingEvent(Player player, FishingHook hook) {
        super(player);
        this.hook = hook;
    }

    public FishingHook getHook() {
        return hook;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

}
