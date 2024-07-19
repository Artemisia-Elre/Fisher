package net.artemisia.dev.api;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.BossEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Information {
    String MODID = "fisher";
    Logger logger = LogManager.getLogger(MODID);


}
