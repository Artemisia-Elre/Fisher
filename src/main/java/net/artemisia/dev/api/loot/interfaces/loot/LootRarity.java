package net.artemisia.dev.api.loot.interfaces.loot;

import net.artemisia.dev.api.configuration.object.loot.Loot;
import net.minecraft.network.chat.Component;

public enum LootRarity {
    JUNK("junk","§8",3),
    COMMON("common","§7",4),
    RARE("rare","§9",6),
    LEGENDARY("legendary","§e",7),
    EXOTICA("exotica","§d",5),
    MYSTERIOUS("mysterious","§c",8);
    private final String name;
    private final String color;
    private final int count;
    LootRarity(String name,String color,int count){
        this.name = name;
        this.color = color;
        this.count = count;
    }

    public static LootRarity fromName(String name) {
        for (LootRarity rarity : LootRarity.values()) {
            if (rarity.getName().equalsIgnoreCase(name.toLowerCase())) {
                return rarity;
            }
        }
        return LootRarity.JUNK;
    }

    @Override
    public String toString() {
        return color + Component.translatable("rarity." + this.name).getString();
    }

    private String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
