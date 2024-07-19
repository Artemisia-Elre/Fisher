package net.artemisia.dev.api.loot.objects.loot.item;

import net.artemisia.dev.api.configuration.object.loot.item.LootItem;
import net.artemisia.dev.api.loot.interfaces.loot.item.ILootItem;
import net.artemisia.dev.api.loot.interfaces.loot.item.IItemAttribute;

import java.util.ArrayList;
import java.util.List;

public class FishingLootItem implements ILootItem {
    private final LootItem item;
    public FishingLootItem(LootItem item) {
        this.item = item;
    }


    @Override
    public String getId() {
        return item.getId();
    }

    @Override
    public String getDisplayName() {
        return item.getDisplayName();
    }

    @Override
    public int getCount() {
        return item.getCount();
    }

    @Override
    public List<String> getLore() {
        return item.getLore();
    }

    @Override
    public List<IItemAttribute> getAttribute() {
        List<IItemAttribute> attributes = new ArrayList<>();
        for (String id : item.getAttributes().keySet()){
            attributes.add(new FishingItemAttribute(id,item.getAttributes().get(id)));
        }
        return attributes;
    }
}
