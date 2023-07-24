package com.senla.car.client;

public interface TypeMicroserviceClient {

    /**
     * Checks if car type with specified name exists
     * @param name Car type name
     * @return True if car type with specified name exists, otherwise false
     */
    Boolean existByName(String name);

}
