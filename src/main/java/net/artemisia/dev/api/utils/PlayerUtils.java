package net.artemisia.dev.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.server.ServerLifecycleHooks;

public class PlayerUtils {
    private final Player player;
    public PlayerUtils(final Player player) {
        this.player = player;
    }
    public static void sendServerMessage(final Component message){
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        for (ServerPlayer player : server.getPlayerList().getPlayers()){
            player.sendSystemMessage(message);
        }
    }

    public void sendTitle(Component text,Component subtitle){

        ClientboundSetTitleTextPacket packet = new ClientboundSetTitleTextPacket(text);
        ClientboundSetSubtitleTextPacket packet2 = new ClientboundSetSubtitleTextPacket(subtitle);
        try {
            ((ServerPlayer) player).connection.send(packet);
            ((ServerPlayer) player).connection.send(packet2);
        }catch (Exception e) {
            e.printStackTrace();
        }
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
