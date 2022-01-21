package ru.iusupov.trafficjams.service;

public interface TrafficJamService {

    /**
     * Gives traffic jam value
     * @return digit from 0 to 9 or -1 if error
     */
    int getValue();

    /**
     * Checks if all necessary stuff is working for performing {@link #getValue()} method
     */
    void systemCheck();

}
