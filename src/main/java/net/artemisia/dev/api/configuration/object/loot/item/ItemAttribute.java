package net.artemisia.dev.api.configuration.object.loot.item;

public class ItemAttribute {
    private int op;
    private int value;
    private String slot;

    public String getSlot(){ return slot; }
    public void setSlot(final String slot){ this.slot = slot; }
    public int getOp() {
        return op;
    }
    public void setOp(int op) {
        this.op = op;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
