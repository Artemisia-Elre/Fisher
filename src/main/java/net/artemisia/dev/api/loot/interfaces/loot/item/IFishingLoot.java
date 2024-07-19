package net.artemisia.dev.api.loot.interfaces.loot.item;

import net.artemisia.dev.api.loot.interfaces.loot.LootRarity;

public interface IFishingLoot {
    String id();
    LootRarity rarity();
    double weight();
    ILootItem item();

}
