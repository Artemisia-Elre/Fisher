package net.artemisia.dev.api.configuration;

public class Default {
    private double cooldown;
    private boolean displayBossBar;
    private double IncreasesOfLeftMoveSpeed;
    private double IncreasesOfRightMoveSpeed;
    private boolean allowPlayerOverWater;
    private boolean allowFishOnLand;
    public Default(double cooldown, boolean displayBossBar, double IncreasesOfLeftMoveSpeed, double IncreasesOfRightMoveSpeed,  boolean allowPlayerOverWater, boolean allowFishOnLand) {
        this.cooldown = cooldown;
        this.displayBossBar = displayBossBar;
        this.IncreasesOfLeftMoveSpeed = IncreasesOfLeftMoveSpeed;
        this.IncreasesOfRightMoveSpeed = IncreasesOfRightMoveSpeed;
        this.allowPlayerOverWater = allowPlayerOverWater;
        this.allowFishOnLand = allowFishOnLand;
    }

    public boolean getAllowFishOnLand() {
        return allowFishOnLand;
    }

    public boolean getAllowPlayerOverWater() {
        return allowPlayerOverWater;
    }

    public void setAllowFishOnLand(boolean allowFishOnLand) {
        this.allowFishOnLand = allowFishOnLand;
    }

    public void setAllowPlayerOverWater(boolean allowPlayerOverWater) {
        this.allowPlayerOverWater = allowPlayerOverWater;
    }

    public double getCooldown() {
        return cooldown;
    }
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }
    public boolean isDisplayBossBar() {
        return displayBossBar;
    }
    public void setDisplayBossBar(boolean displayBossBar) {
        this.displayBossBar = displayBossBar;
    }
    public double getIncreasesOfLeftMoveSpeed() {
        return IncreasesOfLeftMoveSpeed;
    }
    public void setIncreasesOfLeftMoveSpeed(double IncreasesOfLeftMoveSpeed) {
        this.IncreasesOfLeftMoveSpeed = IncreasesOfLeftMoveSpeed;
    }
    public double getIncreasesOfRightMoveSpeed() {
        return IncreasesOfRightMoveSpeed;
    }
    public void setIncreasesOfRightMoveSpeed(double IncreasesOfRightMoveSpeed) {
        this.IncreasesOfRightMoveSpeed = IncreasesOfRightMoveSpeed;
    }
}
