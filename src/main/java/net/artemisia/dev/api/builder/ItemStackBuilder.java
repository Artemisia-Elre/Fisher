package net.artemisia.dev.api.builder;

import net.artemisia.dev.api.loot.interfaces.loot.item.IItemAttribute;
import net.artemisia.dev.api.loot.interfaces.loot.item.ILootItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class ItemStackBuilder {
    private final ItemStack stack;
    public ItemStackBuilder(final ILootItem loot) {
        ResourceLocation res = new ResourceLocation(loot.getId().split(":")[0], loot.getId().split(":")[1]);
        Item item = ForgeRegistries.ITEMS.getValue(res);
        if(item != null) {
            this.stack = new ItemStack(item);
            stack.setCount(loot.getCount());
            stack.setHoverName(Component.literal(loot.getDisplayName()));
            ListTag lore = new ListTag();
            for (String i : loot.getLore()) {
                lore.add(StringTag.valueOf(Component.Serializer.toJson(Component.literal(i))));
            }
            stack.getOrCreateTagElement("display").put("Lore", lore);
            ListTag modifiers = new ListTag();
            for (IItemAttribute attribute : loot.getAttribute()){
                CompoundTag tag = new CompoundTag();
                tag.putString("AttributeName",attribute.getId());
                if (attribute.getSlot() != null){
                    tag.putString("Slot", attribute.getSlot().name);
                }
                tag.putInt("Operation",attribute.getOP());
                tag.putUUID("UUID", UUID.randomUUID());
                tag.putDouble("Amount",attribute.getValue());
                modifiers.add(tag);
            }
            stack.addTagElement("AttributeModifiers", modifiers);
        }else {
            this.stack = null;
        }
    }

    public ItemStack getStack() {
        return stack;
    }
}
