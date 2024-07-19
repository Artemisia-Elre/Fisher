package net.artemisia.dev.api;

import net.artemisia.dev.api.loot.objects.FishingTable;
import net.artemisia.dev.api.loot.objects.loot.FishingLoot;

import java.util.*;

public class FisherRegistries {
    private static final Map<String, FishingLoot> loots = new HashMap<>();
    private static final Map<String, FishingTable> tables = new HashMap<>();
    public static void register(FishingLoot loot){
        for(String s : loots.keySet()){
            if (Objects.equals(s, loot.id())){
                return;
            }
        }
        loots.put(loot.id(), loot);
    }
    public static void register(FishingTable table){
        for(String s : tables.keySet()){
            if (Objects.equals(s, table.getId())){
                return;
            }
        }
        tables.put(table.getId(), table);
    }
    public static List<String> getLootIds(){
        return new ArrayList<>(loots.keySet());
    }
    public static List<String> getTableIds(){
        return new ArrayList<>(tables.keySet());
    }
    public static FishingLoot getLootById(String id){
        FishingLoot item;
        try {
            item = loots.get(id);
        }catch (Exception e){
            item = null;
        }
        return item;
    }
    public static List<FishingLoot> getLoots() {
        return loots.values().stream().toList();
    }
    public static List<FishingTable> getTables() {
        return tables.values().stream().toList();
    }
}
