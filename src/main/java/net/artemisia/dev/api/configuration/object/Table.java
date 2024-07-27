package net.artemisia.dev.api.configuration.object;

import java.util.List;
import java.util.Map;

public class Table {
    private String id;
    private List<String> biome;
    private String rarity;
    private int power;
    private double probability;
    private Map<String, Double> loots;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getBiome() {
        return biome;
    }

    public void setBiome(List<String> biome) {
        this.biome = biome;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Map<String, Double> getLoots() {
        return loots;
    }

    public void setLoots(Map<String, Double> loots) {
        this.loots = loots;
    }
}
