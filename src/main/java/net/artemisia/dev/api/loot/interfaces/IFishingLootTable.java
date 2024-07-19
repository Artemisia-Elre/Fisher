package net.artemisia.dev.api.loot.interfaces;

import net.artemisia.dev.api.loot.interfaces.loot.ILoots;
import net.artemisia.dev.api.loot.interfaces.loot.LootRarity;

import java.util.List;

public interface IFishingLootTable {
    String getId();
    List<String> getBiome();
    LootRarity getRarity();
    int getPower();
    double getProbability();
    List<ILoots> getLoots();
}
