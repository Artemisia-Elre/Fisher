package net.artemisia.dev.api.loot.objects;


import net.artemisia.dev.api.configuration.object.Table;
import net.artemisia.dev.api.loot.interfaces.loot.LootRarity;
import net.artemisia.dev.api.loot.interfaces.IFishingLootTable;
import net.artemisia.dev.api.loot.interfaces.loot.ILoots;

import java.util.ArrayList;
import java.util.List;

public class FishingTable implements IFishingLootTable {
    private final Table table;

    public FishingTable(Table table) {
        this.table = table;
    }
    @Override
    public String getId() {
        return table.getId();
    }

    @Override
    public List<String> getBiome() {
        return table.getBiome();
    }

    @Override
    public LootRarity getRarity() {
        return LootRarity.fromName(table.getRarity());
    }

    @Override
    public int getPower() {
        return table.getPower();
    }

    @Override
    public double getProbability() {
        return table.getProbability();
    }

    @Override
    public List<ILoots> getLoots() {
        List<ILoots> loots = new ArrayList<>();
        for (String id : table.getLoots().keySet()){
            loots.add(new ILoots() {
                @Override
                public String getId() {
                    return id;
                }

                @Override
                public double getProbability() {
                    return table.getProbability();
                }
            });
        }
        return loots;
    }
}