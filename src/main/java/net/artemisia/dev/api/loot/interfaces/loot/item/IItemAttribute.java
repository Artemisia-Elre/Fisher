package net.artemisia.dev.api.loot.interfaces.loot.item;

import net.artemisia.dev.api.loot.interfaces.loot.LootSlot;

public interface IItemAttribute {
    String getId();
    LootSlot getSlot();
    int getOP();
    double getValue();
}
