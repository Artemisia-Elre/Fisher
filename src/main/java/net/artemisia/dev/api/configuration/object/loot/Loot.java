package net.artemisia.dev.api.configuration.object.loot;

import net.artemisia.dev.api.configuration.object.loot.item.LootItem;

public class Loot {
    private String id;
    private String rarity;
    private double weight;
    private LootItem item;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRarity() {
        return rarity;
    }
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public LootItem getItem() {
        return item;
    }
    public void setItem(LootItem item) {
        this.item = item;
    }
}
