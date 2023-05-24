package com.senla.car.dao.graphs;

import com.senla.car.model.Car;
import com.senla.car.model.Car_;
import com.senla.car.model.Registration;
import com.senla.car.model.Registration_;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;

public class CarEntityGraphBuilder {

    public static EntityGraph<Car> buildCarGraph(EntityManager entityManager) {
        EntityGraph<Car> entityGraph = entityManager.createEntityGraph(Car.class);
        entityGraph.addAttributeNodes(Car_.REGISTRATION, Car_.CONDITION,  Car_.STATUS);
        entityGraph.addSubgraph(Car_.registration).addAttributeNodes(Registration_.COLOR, Registration_.TYPE);
        return entityGraph;
    }

    public static EntityGraph<Registration> buildRegistrationGraph(EntityManager entityManager) {
        EntityGraph<Registration> entityGraph = entityManager.createEntityGraph(Registration.class);
        entityGraph.addAttributeNodes(Registration_.COLOR, Registration_.TYPE);
        return entityGraph;
    }

}
