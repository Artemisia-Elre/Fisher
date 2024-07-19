package net.artemisia.dev.api.utils;

import net.artemisia.dev.api.attribute.FisherAttributes;
import net.minecraft.world.entity.player.Player;

public class AttributeUtils {
    private final Player player;
    public AttributeUtils(final Player player) {
        this.player = player;
    }
    public double getFishingPower(){
        return player.getAttributeValue(FisherAttributes.FISHING_POWER.get());
    }
    public double getFishingRate(){
        return player.getAttributeValue(FisherAttributes.FISHING_RATE.get());
    }
    public double getLineLength(){
        return player.getAttributeValue(FisherAttributes.FISHING_LINE_LENGTH.get());
    }
}
