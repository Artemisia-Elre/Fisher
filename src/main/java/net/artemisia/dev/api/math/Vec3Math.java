package net.artemisia.dev.api.math;

public class Vec3Math {
    private final double x,y,z;
    public Vec3Math(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getDistance(Vec3Math vec3) {
        //calculateEuclideanDistance
        double[] v1 = {this.x,this.y,this.z};
        double[] v2 = {vec3.getX(),vec3.getY(),vec3.getZ()};
        double distance = 0.0;
        for (int i = 0; i < v1.length; i++) {
            distance += Math.pow(v1[i] - v2[i], 2);
        }
        return Math.sqrt(distance);
    }

}
