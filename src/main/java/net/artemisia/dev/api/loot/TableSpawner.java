package net.artemisia.dev.api.loot;

import net.artemisia.dev.api.FisherRegistries;
import net.artemisia.dev.api.loot.interfaces.loot.ILoots;
import net.artemisia.dev.api.loot.interfaces.loot.item.ILootItem;
import net.artemisia.dev.api.loot.objects.FishingTable;
import net.artemisia.dev.api.loot.objects.loot.FishingLoot;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class TableSpawner{
    public final double probability;
    private final Biome biome;
    private final double power;
    private final Level level;
    public TableSpawner(double probability, Level level, Biome biome, double power){
        this.probability = probability;
        this.biome = biome;
        this.power = power;
        this.level = level;
    }
    public FishingLoot getLoot(){
        List<FishingTable> list = new ArrayList<>(FisherRegistries.getTables());
        Collections.shuffle(list);
        for (FishingTable table : list){
            if (probability <= table.getProbability() && table.getBiome().contains(Objects.requireNonNull(level.registryAccess().registryOrThrow(ForgeRegistries.BIOMES.getRegistryKey()).getKey(biome)).toString()) && power >= table.getPower()){
                Random random = new Random();
                double d = random.nextDouble();
                List<ILoots> items = new ArrayList<>(table.getLoots());
                Collections.shuffle(items);
                for (ILoots item : items){
                    if (d <= item.getProbability()){
                        return FisherRegistries.getLootById(item.getId());
                    }
                }
            }

        }
        return null;
    }
}
