package net.artemisia.dev.api.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class HookUtils {
    FishingHook hook;
    List<BlockState> states = List.of(
            Blocks.KELP.defaultBlockState(),
            Blocks.SEAGRASS.defaultBlockState(),
            Blocks.TALL_SEAGRASS.defaultBlockState(),
            Blocks.KELP_PLANT.defaultBlockState(),
            Blocks.WATER.defaultBlockState()
    );
    public HookUtils(FishingHook hook) {
        this.hook = hook;
    }
    public boolean isOutOfWater(){
        Level level = hook.level();
        return  !states.contains(level.getBlockState(new BlockPos((int) hook.getX() + 1, (int) hook.getY(), (int) hook.getZ() + 1)))
                || !states.contains(level.getBlockState(new BlockPos((int) hook.getX() - 1, (int) hook.getY(), (int) hook.getZ() - 1)))
                || !states.contains(level.getBlockState(new BlockPos((int) hook.getX(), (int) hook.getY(), (int) hook.getZ() - 1)))
                || !states.contains(level.getBlockState(new BlockPos((int) hook.getX() - 1, (int) hook.getY(), (int) hook.getZ())))
                || !states.contains(level.getBlockState(new BlockPos((int) hook.getX() + 1, (int) hook.getY(), (int) hook.getZ())))
                || !states.contains(level.getBlockState(new BlockPos((int) hook.getX(), (int) hook.getY(), (int) hook.getZ() + 1)));
    }
}
