package net.artemisia.dev.api.loot.objects.loot;

import net.artemisia.dev.api.configuration.object.loot.Loot;
import net.artemisia.dev.api.loot.interfaces.loot.item.IFishingLoot;
import net.artemisia.dev.api.loot.interfaces.loot.item.ILootItem;
import net.artemisia.dev.api.loot.interfaces.loot.LootRarity;
import net.artemisia.dev.api.loot.objects.loot.item.FishingLootItem;

public class FishingLoot implements IFishingLoot{
    private final Loot loot;
    public FishingLoot(Loot loot) {
        this.loot = loot;
    }


    @Override
    public String id() {
        return loot.getId();
    }

    @Override
    public LootRarity rarity() {
        return LootRarity.fromName(loot.getRarity());
    }

    @Override
    public double weight() {
        return loot.getWeight();
    }

    @Override
    public ILootItem item() {
        return new FishingLootItem(loot.getItem());
    }

    @Override
    public String toString() {
        return id();
    }
}
