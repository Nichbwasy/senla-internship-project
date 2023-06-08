package com.senla.car.dao.impl;

import com.senla.car.dao.StatusesRepository;
import com.senla.car.model.Status;
import com.senla.car.model.Status_;
import com.senla.common.dao.CommonCrudRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class StatusesRepositoryImpl extends CommonCrudRepository<Status, Long> implements StatusesRepository {

    @Override
    public Boolean existsByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Status> criteriaQuery = criteriaBuilder.createQuery(Status.class);
        Root<Status> root = criteriaQuery.from(Status.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Status_.name), name));
        return entityManager.createQuery(criteriaQuery).getResultList().size() > 0;
    }
}
