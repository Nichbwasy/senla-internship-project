package com.senla.car.dao.impl;

import com.senla.car.dao.TypesRepository;
import com.senla.car.model.Type;
import com.senla.car.model.Type_;
import com.senla.common.dao.CommonCrudRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class TypesRepositoryImpl extends CommonCrudRepository<Type, Long> implements TypesRepository {

    @Override
    public Boolean existsByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Type> criteriaQuery = criteriaBuilder.createQuery(Type.class);
        Root<Type> root = criteriaQuery.from(Type.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Type_.name), name));
        return entityManager.createQuery(criteriaQuery).getResultList().size() > 0;
    }
}
