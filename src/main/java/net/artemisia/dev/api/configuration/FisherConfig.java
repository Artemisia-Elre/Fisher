package net.artemisia.dev.api.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.artemisia.dev.api.FisherRegistries;
import net.artemisia.dev.api.Information;
import net.artemisia.dev.api.builder.JsonBuilder;
import net.artemisia.dev.api.loot.objects.FishingTable;
import net.artemisia.dev.api.loot.objects.loot.FishingLoot;
import net.artemisia.dev.api.utils.FileUtils;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class FisherConfig {
    private static final File config = new File(getConfigFile());
    private static final File loots = new File(getLootFile());
    private static final File tables = new File(getTableFile());
    private static final File configFile = new File(getConfigFile() + "\\config.json");
    public static Default global;


    public static void init(){

        if (!config.exists()){
            Information.logger.atInfo().log("The Config File isn`t exists, make new folder:{}", getConfigFile());
            config.mkdir();
        }
        if (!loots.exists()){
            Information.logger.atInfo().log("The Config File isn`t exists, make new folder:{}", getLootFile());
            loots.mkdir();
        }
        if (!tables.exists()){
            Information.logger.atInfo().log("The Config File isn`t exists, make new folder:{}", getTableFile());
            tables.mkdir();
        }


        load();
    }

    public static void load() {
        if (!configFile.exists()){
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(new Default(6,true,0,0,true,true));
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<File> loots = new FileUtils(FisherConfig.loots).getFiles();
        List<File> tables = new FileUtils(FisherConfig.tables).getFiles();
        for (File i : loots){
            FishingLoot loot = new FishingLoot(new JsonBuilder(new FileUtils(i).readLines()).getLoot());
            Information.logger.info(loot.toString());
            FisherRegistries.register(loot);
        }
        for (File i : tables){
            FishingTable table = new FishingTable(new JsonBuilder((new FileUtils(i).readLines())).getTable());
            FisherRegistries.register(table);
        }
        global = new Gson().fromJson(new FileUtils(configFile).readLines(),Default.class);
    }


    public static String getLootFile(){
        return FMLPaths.CONFIGDIR.get().toString() + "\\Fisher\\Loots\\";
    }
    public static String getTableFile(){
        return FMLPaths.CONFIGDIR.get().toString() + "\\Fisher\\Tables";
    }

    public static String getConfigFile(){
        return FMLPaths.CONFIGDIR.get().toString() + "\\Fisher\\";
    }
}
