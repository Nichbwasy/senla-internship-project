package com.senla.car.dao.impl;

import com.senla.car.dao.RegistrationsRepository;
import com.senla.car.dao.graphs.CarEntityGraphBuilder;
import com.senla.car.model.Color_;
import com.senla.car.model.Registration;
import com.senla.car.model.Registration_;
import com.senla.car.model.Type_;
import com.senla.common.constants.graphs.GraphTypes;
import com.senla.common.dao.CommonCrudRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegistrationRepositoryImpl extends CommonCrudRepository<Registration, Long> implements RegistrationsRepository {

    @Override
    public List<Registration> selectAllByModel(String modelName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Registration> criteriaQuery = criteriaBuilder.createQuery(Registration.class);
        Root<Registration> root = criteriaQuery.from(Registration.class);

        EntityGraph<Registration> entityGraph = CarEntityGraphBuilder.buildRegistrationGraph(entityManager);

        criteriaQuery.select(root).where(criteriaBuilder.like(root.get(Registration_.model), "%" + modelName + "%"));
        return entityManager.createQuery(criteriaQuery).setHint(GraphTypes.LOAD, entityGraph).getResultList();
    }

    @Override
    public List<Registration> selectAllWithAllowedMaxMassMoreThen(Integer allowedMass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Registration> criteriaQuery = criteriaBuilder.createQuery(Registration.class);
        Root<Registration> root = criteriaQuery.from(Registration.class);

        EntityGraph<Registration> entityGraph = CarEntityGraphBuilder.buildRegistrationGraph(entityManager);

        criteriaQuery.select(root).where(criteriaBuilder.gt(root.get(Registration_.allowedMaxMass), allowedMass));
        return entityManager.createQuery(criteriaQuery).setHint(GraphTypes.LOAD, entityGraph).getResultList();
    }

    @Override
    public List<Registration> selectAllByYearsDiapason(Integer minYear, Integer maxYear) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Registration> criteriaQuery = criteriaBuilder.createQuery(Registration.class);
        Root<Registration> root = criteriaQuery.from(Registration.class);

        EntityGraph<Registration> entityGraph = CarEntityGraphBuilder.buildRegistrationGraph(entityManager);

        criteriaQuery.select(root).where(criteriaBuilder.between(root.get(Registration_.releaseYear), minYear, maxYear));
        return entityManager.createQuery(criteriaQuery).setHint(GraphTypes.LOAD, entityGraph).getResultList();
    }

    @Override
    public List<Registration> selectAllByAllowedMaxMassDiapason(Integer min, Integer max) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Registration> criteriaQuery = criteriaBuilder.createQuery(Registration.class);
        Root<Registration> root = criteriaQuery.from(Registration.class);

        EntityGraph<Registration> entityGraph = CarEntityGraphBuilder.buildRegistrationGraph(entityManager);

        criteriaQuery.select(root).where(criteriaBuilder.between(root.get(Registration_.allowedMaxMass), min, max));
        return entityManager.createQuery(criteriaQuery).setHint(GraphTypes.LOAD, entityGraph).getResultList();
    }

    @Override
    public List<Registration> selectAllByUnladenMassDiapason(Integer min, Integer max) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Registration> criteriaQuery = criteriaBuilder.createQuery(Registration.class);
        Root<Registration> root = criteriaQuery.from(Registration.class);

        EntityGraph<Registration> entityGraph = CarEntityGraphBuilder.buildRegistrationGraph(entityManager);

        criteriaQuery.select(root).where(criteriaBuilder.between(root.get(Registration_.unladenMass), min, max));
        return entityManager.createQuery(criteriaQuery).setHint(GraphTypes.LOAD, entityGraph).getResultList();
    }

    @Override
    public List<Registration> selectAllByColor(String colorName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Registration> criteriaQuery = criteriaBuilder.createQuery(Registration.class);
        Root<Registration> root = criteriaQuery.from(Registration.class);

        EntityGraph<Registration> entityGraph = CarEntityGraphBuilder.buildRegistrationGraph(entityManager);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Registration_.color).get(Color_.name), colorName));
        return entityManager.createQuery(criteriaQuery).setHint(GraphTypes.LOAD, entityGraph).getResultList();
    }

    @Override
    public List<Registration> selectAllByType(String typeName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Registration> criteriaQuery = criteriaBuilder.createQuery(Registration.class);
        Root<Registration> root = criteriaQuery.from(Registration.class);

        EntityGraph<Registration> entityGraph = CarEntityGraphBuilder.buildRegistrationGraph(entityManager);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Registration_.type).get(Type_.name), typeName));
        return entityManager.createQuery(criteriaQuery).setHint(GraphTypes.LOAD, entityGraph).getResultList();
    }
}
