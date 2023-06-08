package com.senla.car.dao.impl;

import com.senla.car.dao.ConditionsRepository;
import com.senla.car.model.Condition;
import com.senla.car.model.Condition_;
import com.senla.common.dao.CommonCrudRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionsRepositoryImpl extends CommonCrudRepository<Condition, Long> implements ConditionsRepository {

    @Override
    public Boolean existsByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Condition> criteriaQuery = criteriaBuilder.createQuery(Condition.class);
        Root<Condition> root = criteriaQuery.from(Condition.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Condition_.name), name));
        return entityManager.createQuery(criteriaQuery).getResultList().size() > 0;
    }
}
