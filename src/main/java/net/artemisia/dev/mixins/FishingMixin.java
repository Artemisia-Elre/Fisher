package net.artemisia.dev.mixins;


import net.artemisia.dev.api.builder.ItemStackBuilder;
import net.artemisia.dev.api.configuration.FisherConfig;
import net.artemisia.dev.api.event.PlayerFishingEvent;
import net.artemisia.dev.api.loot.TableSpawner;
import net.artemisia.dev.api.loot.interfaces.loot.LootRarity;
import net.artemisia.dev.api.loot.objects.loot.FishingLoot;
import net.artemisia.dev.api.math.FMath;
import net.artemisia.dev.api.math.Vec3Math;
import net.artemisia.dev.api.utils.AttributeUtils;
import net.artemisia.dev.api.utils.HookUtils;
import net.artemisia.dev.api.utils.PlayerUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

import java.util.*;

@Mixin(FishingHook.class)
public abstract class FishingMixin extends Projectile {

    protected FishingMixin(EntityType<? extends Projectile> p_37248_, Level p_37249_){
        super(p_37248_, p_37249_);

    }

    @Unique
    private double fisher$tieRodOdds = 100D;
    @Unique
    private double fisher$rate = 0;
    @Unique
    private double fisher$cooldown = 0.0f;
    @Unique
    private FishingLoot fisher$loot;
    @Unique
    ServerBossEvent fisher$bossBar = new ServerBossEvent(Component.nullToEmpty("进度条测试文本"), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.NOTCHED_6);
    @Unique
    private ItemStack fisher$itemstack = ItemStack.EMPTY;

    @Shadow @Nullable public abstract Player getPlayerOwner();
    @Shadow public abstract void remove(Entity.@NotNull RemovalReason pReason);
    @Shadow protected abstract boolean shouldStopFishing(Player player);
    @Shadow @Nullable private Entity hookedIn;
    @Shadow protected abstract void pullEntity(Entity p_150156_);
    @Shadow private int nibble;
    @Shadow private int timeUntilLured;
    @Shadow @Final private static EntityDataAccessor<Boolean> DATA_BITING;
    @Shadow private float fishAngle;
    @Shadow private int timeUntilHooked;
    @Shadow @Final private int lureSpeed;
    @Shadow @Final private RandomSource syncronizedRandom;
    @Shadow @Final private static EntityDataAccessor<Integer> DATA_HOOKED_ENTITY;
    @Shadow private boolean biting;
    @Shadow public abstract void tick();

    @Shadow @Final private int luck;

    /**
     * @author Artemisia
     */
    @Inject(method = "tick",at = @At("HEAD"))
    public void tick_head(CallbackInfo ci) {
        if (new PlayerUtils(getPlayerOwner()).isOverWater() && !FisherConfig.global.getAllowPlayerOverWater()){
            new PlayerUtils(getPlayerOwner()).sendActionBar(Component.translatable("message.in.water"));
            this.discard();
        }
        Random random = new Random();
        int randomInt = random.nextInt(11);
        if (this.biting){
            if (new HookUtils((FishingHook) (Object)this).isOutOfWater() && !FisherConfig.global.getAllowFishOnLand()){
                new PlayerUtils(getPlayerOwner()).sendActionBar(Component.translatable("message.fish.onland"));
                this.discard();
            }
            double d1 = fisher$randomHorizontalVelocity();
            this.fisher$cooldown += 1;
            AttributeUtils utils = new AttributeUtils(getPlayerOwner());
            Vec3Math math = new Vec3Math(Objects.requireNonNull(getPlayerOwner()).getX(), getPlayerOwner().getY(), getPlayerOwner().getZ());
            double length = math.getDistance(new Vec3Math(this.getX(),this.getY(),this.getZ()));
            if (randomInt > 5){
                if (length > utils.getLineLength()){
                    getPlayerOwner().setDeltaMovement(d1 + FisherConfig.global.getIncreasesOfLeftMoveSpeed(),0,0);
                }
                this.setDeltaMovement(
                        d1 + FisherConfig.global.getIncreasesOfLeftMoveSpeed(),
                        0,
                        0
                );
            }else {
                if (length > utils.getLineLength()){
                    getPlayerOwner().setDeltaMovement(0,0,d1 + FisherConfig.global.getIncreasesOfRightMoveSpeed());
                }
                this.setDeltaMovement(
                        0,
                        0,
                        d1 + FisherConfig.global.getIncreasesOfRightMoveSpeed()
                );
            }
        }else {
            PlayerFishingEvent event = new PlayerFishingEvent(getPlayerOwner(), Objects.requireNonNull(getPlayerOwner()).fishing);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled()){
                this.discard();
            }
        }
        if (nibble < 0){
            try {
                fisher$bossBar.removePlayer((ServerPlayer) getPlayerOwner());
            }catch (Exception ignored){

            }
        }


    }

    /**
     * @author Artemisia
     * @reason I don`t know
     */
    @Overwrite
    private void catchingFish(@NotNull BlockPos p_37146_) {
        ServerLevel serverlevel = (ServerLevel)this.level();
        int i = 1;
        BlockPos blockpos = p_37146_.above();
        if (this.random.nextFloat() < 0.25F && this.level().isRainingAt(blockpos)) {
            ++i;
        }

        if (this.random.nextFloat() < 0.5F && !this.level().canSeeSky(blockpos)) {
            --i;
        }

        if (this.nibble > 0) {
            --this.nibble;
            if (this.nibble <= 0) {
                this.timeUntilLured = 0;
                this.timeUntilHooked = 0;
                this.getEntityData().set(DATA_BITING, false);
            }
        } else {
            float f5;
            float f6;
            float f7;
            double d4;
            double d5;
            double d6;
            BlockState blockstate1;
            if (this.timeUntilHooked > 0) {
                this.timeUntilHooked -= i;
                if (this.timeUntilHooked > 0) {
                    this.fishAngle += (float)this.random.triangle(0.0, 9.188);
                    f5 = this.fishAngle * 0.017453292F;
                    f6 = Mth.sin(f5);
                    f7 = Mth.cos(f5);
                    d4 = this.getX() + (double)(f6 * (float)this.timeUntilHooked * 0.1F);
                    d5 = (float)Mth.floor(this.getY()) + 1.0F;
                    d6 = this.getZ() + (double)(f7 * (float)this.timeUntilHooked * 0.1F);
                    blockstate1 = serverlevel.getBlockState(BlockPos.containing(d4, d5 - 1.0, d6));
                    if (blockstate1.is(Blocks.WATER)) {
                        if (this.random.nextFloat() < 0.15F) {
                            serverlevel.sendParticles(ParticleTypes.BUBBLE, d4, d5 - 0.10000000149011612, d6, 1, f6, 0.1, f7, 0.0);
                        }

                        float f3 = f6 * 0.04F;
                        float f4 = f7 * 0.04F;
                        serverlevel.sendParticles(ParticleTypes.FISHING, d4, d5, d6, 0, f4, 0.01, -f3, 1.0);
                        serverlevel.sendParticles(ParticleTypes.FISHING, d4, d5, d6, 0, -f4, 0.01, f3, 1.0);
                    }
                } else {
                    this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                    double d3 = this.getY() + 0.5;
                    serverlevel.sendParticles(ParticleTypes.BUBBLE, this.getX(), d3, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0, this.getBbWidth(), 0.20000000298023224);
                    serverlevel.sendParticles(ParticleTypes.FISHING, this.getX(), d3, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0, this.getBbWidth(), 0.20000000298023224);
                    this.nibble = Mth.nextInt(this.random, 20, 40);
                    this.getEntityData().set(DATA_BITING, true);
                }
            } else if (this.timeUntilLured > 0) {
                this.timeUntilLured -= i;
                f5 = 0.15F;
                if (this.timeUntilLured < 20) {
                    f5 += (float)(20 - this.timeUntilLured) * 0.05F;
                } else if (this.timeUntilLured < 40) {
                    f5 += (float)(40 - this.timeUntilLured) * 0.02F;
                } else if (this.timeUntilLured < 60) {
                    f5 += (float)(60 - this.timeUntilLured) * 0.01F;
                }

                if (this.random.nextFloat() < f5) {
                    f6 = Mth.nextFloat(this.random, 0.0F, 360.0F) * 0.017453292F;
                    f7 = Mth.nextFloat(this.random, 25.0F, 60.0F);
                    d4 = this.getX() + (double)(Mth.sin(f6) * f7) * 0.1;
                    d5 = (float)Mth.floor(this.getY()) + 1.0F;
                    d6 = this.getZ() + (double)(Mth.cos(f6) * f7) * 0.1;
                    blockstate1 = serverlevel.getBlockState(BlockPos.containing(d4, d5 - 1.0, d6));
                    if (blockstate1.is(Blocks.WATER)) {
                        serverlevel.sendParticles(ParticleTypes.SPLASH, d4, d5, d6, 2 + this.random.nextInt(2), 0.10000000149011612, 0.0, 0.10000000149011612, 0.0);
                    }
                }

                if (this.timeUntilLured <= 0) {
                    this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
                    this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
                }
            } else {
                this.timeUntilLured = Mth.nextInt(this.random, 100, 600);
                this.timeUntilLured -= this.lureSpeed * 20 * 5;
            }
        }

    }

    /**
     * @author Artemisia
     * @reason None
     */
    @Overwrite
    public int retrieve(ItemStack item){
        Player player = this.getPlayerOwner();
        if (!this.level().isClientSide && player != null && !this.shouldStopFishing(player)) {
            int i = 0;
            ItemFishedEvent event;
            if (this.hookedIn != null) {
                this.pullEntity(this.hookedIn);
                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, item, (FishingHook) (Object)this, Collections.emptyList());
                this.level().broadcastEntityEvent(this, (byte)31);
                i = this.hookedIn instanceof ItemEntity ? 3 : 5;
                this.discard();
            } else if (nibble > 0) {

                event = new ItemFishedEvent(List.of(fisher$itemstack), this.onGround() ? 2 : 1, (FishingHook) (Object) this);
                MinecraftForge.EVENT_BUS.post(event);
                if (event.isCanceled()) {
                    this.discard();
                    return event.getRodDamage();
                }

                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, item,  (FishingHook) (Object) this,List.of(fisher$itemstack));

                if (fisher$bossBar.getProgress() >= 1.0F){
                    ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), fisher$itemstack);
                    double d0 = player.getX() - this.getX();
                    double d1 = player.getY() - this.getY();
                    double d2 = player.getZ() - this.getZ();
                    itementity.setDeltaMovement(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);
                    this.level().addFreshEntity(itementity);
                    player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5, player.getZ() + 0.5, this.random.nextInt(6) + 1));
                    if (fisher$itemstack.is(ItemTags.FISHES)) {
                        player.awardStat(Stats.FISH_CAUGHT, 1);
                    }
                    fisher$bossBar.removePlayer((ServerPlayer) Objects.requireNonNull(getPlayerOwner()));
                    this.discard();
                } else{
                    if (fisher$cooldown >= FisherConfig.global.getCooldown()){
                        if (new Random().nextDouble() <= fisher$rate){
                            fisher$bossBar.setProgress(fisher$bossBar.getProgress() + (((float) 1 / fisher$loot.rarity().getCount()) * 3));
                        }else if (new Random().nextDouble() <= fisher$tieRodOdds){
                            fisher$bossBar.setProgress(fisher$bossBar.getProgress() + ((float) 1 / fisher$loot.rarity().getCount()));
                        }else{
                            new PlayerUtils(getPlayerOwner()).sendActionBar(Component.translatable("message.tie.rod.failed"));
                        }
                        fisher$cooldown = 0;
                    }
                    fisher$bossBar.setName(Component.translatable("message.fishing.item", fisher$itemstack.getDisplayName().getString(), fisher$loot.rarity().toString(), (int) (fisher$tieRodOdds * 100) + "%", (int) (fisher$rate * 100) + "%"));
                    this.nibble = Mth.nextInt(this.random, 20, 40);
                    this.getEntityData().set(DATA_BITING, true);
                }
                i = 1;
            }else {
                this.discard();
            }

            if (this.onGround()) {
                i = 2;
            }

            return i;
        } else {
            return 0;
        }
    }

    @Unique
    private double fisher$randomHorizontalVelocity() {
        Random random = new Random();
        return random.nextDouble() * 0.4;
    }

    /**
     * @author Artemisia
     * @reason None
     */
    @Overwrite
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entitydata) {
        if (DATA_HOOKED_ENTITY.equals(entitydata)) {
            int i = this.getEntityData().get(DATA_HOOKED_ENTITY);
            this.hookedIn = i > 0 ? this.level().getEntity(i - 1) : null;
        }

        if (DATA_BITING.equals(entitydata)) {
            this.biting = this.getEntityData().get(DATA_BITING);
            if (this.biting) {
                TableSpawner spawner = new TableSpawner(new Random().nextDouble(),level(),level().getBiome(this.blockPosition()).get(),new AttributeUtils(getPlayerOwner()).getFishingPower());

                fisher$loot = spawner.getLoot();

                if (fisher$loot != null) {
                    this.fisher$tieRodOdds = FMath.getSuccessOdds(new AttributeUtils(getPlayerOwner()).getFishingPower(), fisher$loot.weight());
                    this.fisher$rate = FMath.getSuccessOddsRate(new AttributeUtils(getPlayerOwner()).getFishingPower(),fisher$loot.weight(),luck,new AttributeUtils(getPlayerOwner()).getFishingRate());
                    fisher$itemstack = new ItemStackBuilder(fisher$loot.item()).getStack();
                    if (
                            fisher$loot.rarity() == LootRarity.RARE
                            || fisher$loot.rarity() == LootRarity.LEGENDARY
                            || fisher$loot.rarity() == LootRarity.EXOTICA
                            || fisher$loot.rarity() == LootRarity.MYSTERIOUS
                    ){
                        new PlayerUtils(getPlayerOwner()).sendTitle(Component.translatable("message.fishing.out.title",fisher$loot.rarity().toString()));
                    }
                    if (fisher$loot.rarity() == LootRarity.LEGENDARY || fisher$loot.rarity() == LootRarity.MYSTERIOUS){
                        PlayerUtils.sendServerMessage(Component.translatable("message.who.fishing.out", Objects.requireNonNull(getPlayerOwner()).getName().getString(),fisher$loot.rarity().toString()));
                    }
                    try {
                        fisher$bossBar.setProgress(0);
                        fisher$bossBar.setName(Component.translatable("message.fishing.item", fisher$itemstack.getDisplayName().getString(), fisher$loot.rarity().toString(), (int) (fisher$tieRodOdds * 100) + "%", (int) (fisher$rate * 100) + "%"));
                        fisher$bossBar.setVisible(FisherConfig.global.isDisplayBossBar());
                        fisher$bossBar.addPlayer((ServerPlayer) Objects.requireNonNull(getPlayerOwner()));
                    }catch (NullPointerException ignored){

                    }
                }else {
                    new PlayerUtils(getPlayerOwner()).sendActionBar(Component.translatable("message.no.such.loot"));
                    this.discard();
                }
                this.setDeltaMovement(this.getDeltaMovement().x, -0.4F * Mth.nextFloat(this.syncronizedRandom, 0.6F, 1.0F), this.getDeltaMovement().z);
            }
        }

        super.onSyncedDataUpdated(entitydata);
    }
    @Inject(at = @At("HEAD"),method = "remove")
    public void remove(RemovalReason p_150146_, CallbackInfo ci) {
        try {
            fisher$bossBar.removePlayer((ServerPlayer) Objects.requireNonNull(getPlayerOwner()));
        }catch (Exception ignored){

        }
    }

}
