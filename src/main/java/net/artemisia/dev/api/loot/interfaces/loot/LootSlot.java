package net.artemisia.dev.api.loot.interfaces.loot;

public enum LootSlot {
    MainHand("mainhand"),
    Helmet("helmet"),
    Chestplate("chestplate"),
    Leggings("leggings"),
    Boots("boots"),
    OffHand("offhand");

    public final String name;

    LootSlot(String name){
        this.name = name;
    }

    public static LootSlot fromName(String name) {
        for (LootSlot slot : LootSlot.values()) {
            if (slot.getName().equalsIgnoreCase(name.toLowerCase())) {
                return slot;
            }
        }
        return null;
    }

    private String getName() {
        return name;
    }
}
