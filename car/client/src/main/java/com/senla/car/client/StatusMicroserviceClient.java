package com.senla.car.client;

public interface StatusMicroserviceClient {

    /**
     * Checks if car status with specified name exists
     * @param name Car status name
     * @return True if car status with specified name exists, otherwise false
     */
    Boolean existsByName(String name);

}
