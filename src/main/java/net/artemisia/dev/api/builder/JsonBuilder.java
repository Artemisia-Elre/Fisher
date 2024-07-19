package net.artemisia.dev.api.builder;

import com.google.gson.Gson;
import net.artemisia.dev.api.configuration.object.Table;
import net.artemisia.dev.api.configuration.object.loot.Loot;

public class JsonBuilder {
    private final String source;
    private final Gson gson = new Gson();
    public JsonBuilder(String source) {
        this.source = source;
    }
    public Loot getLoot() {
        return gson.fromJson(source, Loot.class);
    }
    public Table getTable() {
        return gson.fromJson(source, Table.class);
    }
}
