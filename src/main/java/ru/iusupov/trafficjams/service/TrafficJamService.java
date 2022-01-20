package ru.iusupov.trafficjams.service;

public interface TrafficJamService {

    /**
     * Gives traffic jam value
     * @return digit from 0 to 9 or -1 if error
     */
    int getValue();

}
