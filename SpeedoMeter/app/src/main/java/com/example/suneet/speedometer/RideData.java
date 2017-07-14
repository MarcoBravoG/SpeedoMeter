package com.example.suneet.speedometer;

/**
 * Created by suneet on 14/7/17.
 */

public class RideData {
    boolean isFirst;
    private long time;
    private long totalTime;
    private double distanceM;
    private double distanceKM;
    private double currentSpeed;
    private double maxSpeed;
    private double avgSpeed;

    public RideData() {
        isFirst=true;
        time=0;
        totalTime=0;
        distanceKM=0;
        distanceM=0;
        currentSpeed=0;
        maxSpeed=0;
        avgSpeed=0;
    }
    public void distanceTotal(double distance)
    {
        distanceM=distanceM+distance;
        distanceKM=distanceM/1000;

    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public double getDistanceM() {
        return distanceM;
    }

    public void setDistanceM(double distanceM) {
        this.distanceM = distanceM;
    }

    public double getDistanceKM() {
        return distanceKM;
    }

    public void setDistanceKM(double distanceKM) {
        this.distanceKM = distanceKM;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
        if(currentSpeed>maxSpeed)
        {
            maxSpeed=currentSpeed;
        }
    }


    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }
}
