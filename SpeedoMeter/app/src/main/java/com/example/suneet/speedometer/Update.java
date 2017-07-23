package com.example.suneet.speedometer;

/**
 * Created by suneet on 23/7/17.
 */

public interface Update {
    void updateLocation(String location);
    void updateSpeedGauge(float speed);
    void updateTotalDistance(double distance);
    void updateAverage(double avg);
    void updateTopSpeed(double top);


}
