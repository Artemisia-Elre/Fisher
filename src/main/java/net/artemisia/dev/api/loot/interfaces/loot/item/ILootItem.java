package net.artemisia.dev.api.loot.interfaces.loot.item;

import java.util.List;

public interface ILootItem {
    String getId();
    String getDisplayName();
    int getCount();
    List<String> getLore();
    List<IItemAttribute> getAttribute();
}
