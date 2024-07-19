package net.artemisia.dev.api.configuration.object.loot.item;

import java.util.List;
import java.util.Map;

public class LootItem {
    private String id;
    private String displayName;
    private int count;
    private List<String> lore;
    private Map<String, ItemAttribute> Attributes;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public List<String> getLore() {
        return lore;
    }
    public void setLore(List<String> lore) {
        this.lore = lore;
    }
    public Map<String, ItemAttribute> getAttributes() {
        return Attributes;
    }
    public void setAttributes(Map<String, ItemAttribute> attributes) {
        Attributes = attributes;
    }
}
