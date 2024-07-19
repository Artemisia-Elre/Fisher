package net.artemisia.dev.api.loot.objects.loot.item;


import net.artemisia.dev.api.configuration.object.loot.item.ItemAttribute;
import net.artemisia.dev.api.loot.interfaces.loot.LootSlot;
import net.artemisia.dev.api.loot.interfaces.loot.item.IItemAttribute;

public class FishingItemAttribute implements IItemAttribute {
    private final String id;
    private final ItemAttribute attribute;
    public FishingItemAttribute(final String id,final ItemAttribute attribute) {
        this.attribute = attribute;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LootSlot getSlot() {
        return LootSlot.fromName(attribute.getSlot());
    }


    @Override
    public int getOP() {
        return attribute.getOp();
    }

    @Override
    public double getValue() {
        return attribute.getValue();
    }
}
