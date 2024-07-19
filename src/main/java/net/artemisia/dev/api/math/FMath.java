package net.artemisia.dev.api.math;

public class FMath {
    public static double getSuccessOdds(double power,double wight){
        return (power/(wight + power + 2))*0.6 + 0.06;
    }
    public static double getSuccessOddsRate(double power,double wight,double luck,double add){
        return (((( luck * 0.5 ) + (power - wight) * 0.3)) / 100) + add;
    }

}
