package net.artemisia.dev.api.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;

public class PlayerUtils {
    private final Player player;
    public PlayerUtils(final Player player) {
        this.player = player;
    }

    public void sendActionBar(Component text) {
        ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(text);
        try {
            ((ServerPlayer) player).connection.send(packet);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isOverWater(){
        return player.level().getBlockState(new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ())) == Blocks.WATER.defaultBlockState();
    }
}
