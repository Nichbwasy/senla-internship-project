package com.senla.common.dao;

import com.senla.common.dao.model.ModelEntity;
import com.senla.common.exception.repository.*;
import com.senla.common.reflections.GenericUtils;
import jakarta.persistence.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional(readOnly = true)
public abstract class CommonCrudRepository<T extends ModelEntity<I>, I> implements CrudRepository<T, I> {

    private final Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;

    public CommonCrudRepository() {
        this.type = GenericUtils.getGenericParameterClass(this.getClass(), 0);
    }

    @Override
    @Transactional
    public T insert(T t) {
        if (t == null) {
            log.error("Unable insert a new statement! Entity can't be null!");
            throw new InsertStatementRepositoryException("Unable insert a new statement! Entity can't be null!");
        }

        try {
            entityManager.persist(t);
            entityManager.flush();
            return t;
        } catch (EntityExistsException e) {
            throw new InsertStatementRepositoryException(
                    String.format("Unable insert a new statement! Entity already exist! %s", e.getMessage())
            );
        } catch (IllegalArgumentException e) {
            throw new InsertStatementRepositoryException(
                    String.format("Unable insert a new statement! Illegal argument exception! %s", e.getMessage())
            );
        } catch (Exception e) {
            throw new InsertStatementRepositoryException(
                    String.format("Unable insert a new statement! %s", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public T update(T t) {
        if (t == null) {
            log.error("Unable update statement! Entity can't be null!");
            throw new UpdateStatementRepositoryException("Unable update statement! Entity can't be null!");
        }

        try {
            entityManager.merge(t);
            entityManager.flush();
            return t;
        } catch (EntityNotFoundException e) {
            throw new UpdateStatementRepositoryException(
                    String.format("Unable update statement! Entity already exist! %s", e.getMessage())
            );
        } catch (IllegalArgumentException e) {
            throw new UpdateStatementRepositoryException(
                    String.format("Unable update statement! Illegal argument exception! %s", e.getMessage())
            );
        } catch (Exception e) {
            throw new UpdateStatementRepositoryException(
                    String.format("Unable update statement! %s", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public I delete(I id) {
        if (id == null) {
            log.error("Unable delete statement! Id can't be null!");
            throw new DeleteStatementRepositoryException("Unable delete statement! Id can't be null!");
        }

        try {
            entityManager.remove(select(id));
            entityManager.flush();
            return id;
        } catch (EntityNotFoundException e) {
            throw new DeleteStatementRepositoryException(
                    String.format("Unable delete statement! Entity not found! %s", e.getMessage())
            );
        } catch (Exception e) {
            throw new DeleteStatementRepositoryException(
                    String.format("Unable delete statement! %s", e.getMessage())
            );
        }
    }

    @Override
    public T select(I id) {
        if (id == null) {
            log.error("Unable select statement! Id can't be null!");
            throw new SelectStatementRepositoryException("Unable select statement! Id can't be null!");
        }

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
            Root<T> root = criteriaQuery.from(type);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            throw new com.senla.common.exception.repository.EntityNotFoundException(
                    String.format("Entity with id '%s' not found! %s", id, e.getMessage())
            );
        } catch (Exception e) {
            throw new com.senla.common.exception.repository.EntityNotFoundException(
                    String.format("Unable find entity with id '%s'! %s", id, e.getMessage())
            );
        }
    }

    @Override
    public List<T> selectAll() {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
            Root<T> root = criteriaQuery.from(type);

            criteriaQuery.select(root);
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            log.warn("Entities not found! {}", e.getMessage());
            throw new com.senla.common.exception.repository.EntityNotFoundException(
                    String.format("Entities not found! %s", e.getMessage())
            );
        } catch (Exception e) {
            log.error("Entities select exception! {}", e.getMessage());
            throw new com.senla.common.exception.repository.EntityNotFoundException(
                    String.format("Entities select exception! %s", e.getMessage())
            );
        }
    }

    @Override
    public Boolean existById(I id) {
        if (id == null) {
            log.error("Unable find statement! Id can't be null!");
            throw new ExistStatementRepositoryException("Unable find statement! Id can't be null!");
        }

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
            Root<T> root = criteriaQuery.from(type);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            return entityManager.createQuery(criteriaQuery).getResultList().size() > 0;
        } catch (Exception e) {
            log.error("Exception while checking entity with id '{}'! {}", id, e.getMessage());
            throw new com.senla.common.exception.repository.EntityNotFoundException(
                    String.format("Exception while checking entity with id '%s'! %s", id, e.getMessage())
            );
        }
    }

}
