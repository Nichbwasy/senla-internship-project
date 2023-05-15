package com.senla.car.dao.impl;

import com.senla.car.dao.CarsRepository;
import com.senla.car.dao.builders.CarsQueryBuilder;
import com.senla.car.dao.graphs.CarEntityGraphBuilder;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.car.model.Car;
import com.senla.common.constants.graphs.GraphTypes;
import com.senla.common.constants.pagination.PaginationDto;
import com.senla.common.dao.CommonCrudRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CarsRepositoryImpl extends CommonCrudRepository<Car, Long> implements CarsRepository {

    @Override
    public List<Car> selectAllByMileageDiapason(Double min, Double max) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);

        criteriaQuery.select(root).where(criteriaBuilder.between(root.get(Car_.mileage), min, max));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> selectAllByStatusName(String statusName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Car_.status).get(Status_.name), statusName));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> selectAllByConditionNameCriteria(String conditionName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Car_.condition).get(Condition_.name), conditionName));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Car> selectAllByConditionNameJPQL(String conditionName) {
        Query query = entityManager.createQuery(
                "select cr from Car cr join fetch cr.condition con where con.name = '" + conditionName + "'"
        );
        return (List<Car>) query.getResultList();
    }

    @Override
    public Car selectByIdEntityGraphWithFetches(Long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("graph.car.with.all.fetches");

        Map<String, Object> hints = new HashMap<>();
        hints.put(GraphTypes.LOAD, entityGraph);
        return entityManager.find(Car.class, id, hints);
    }

    @Override
    public Car selectByIdEntityGraphWithoutFetches(Long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("graph.car");

        Map<String, Object> hints = new HashMap<>();
        hints.put(GraphTypes.LOAD, entityGraph);
        return entityManager.find(Car.class, id, hints);
    }


    @Override
    public List<Car> selectByFilter(@Valid PaginationDto paginationDto, CarsCatalogFilterForm filterForm) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);

        List<Predicate> predicates = CarsQueryBuilder.buildPredicatesForFilter(criteriaBuilder, root, filterForm);
        List<Order> orders = CarsQueryBuilder.buildOrdersForFilter(criteriaBuilder, root, filterForm);

        criteriaQuery.select(root);
        if (predicates.size() > 0) criteriaQuery.where(predicates.toArray(new Predicate[0]));
        if (orders.size() > 0) criteriaQuery.orderBy(orders);

        EntityGraph<Car> carEntityGraph = CarEntityGraphBuilder.buildCarGraph(entityManager);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((paginationDto.getPage() - 1) * paginationDto.getPageSize())
                .setMaxResults(paginationDto.getPageSize())
                .setHint(GraphTypes.LOAD, carEntityGraph)
                .getResultList();
    }

}
