package com.senla.car.client;

public interface ConditionMicroserviceClient {

    /**
     * Checks if car condition with specified name exists
     * @param name Car condition name
     * @return True if car condition with specified name exists, otherwise false
     */
    Boolean existsByName(String name);

}
