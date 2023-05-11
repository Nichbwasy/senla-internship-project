package com.senla.common.dao;

import com.senla.common.dao.model.ModelEntity;
import com.senla.common.exception.repository.DeleteStatementRepositoryException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.common.exception.repository.UpdateStatementRepositoryException;
import com.senla.common.reflections.GenericUtils;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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
            throw new com.senla.common.exception.repository.EntityNotFoundException(
                    String.format("Entity with id '%s' not found! %s", e.getMessage())
            );
        } catch (Exception e) {
            throw new com.senla.common.exception.repository.EntityNotFoundException(
                    String.format("Unable find entity with id '%s'! %s", e.getMessage())
            );
        }
    }

    @Override
    public Boolean existById(I id) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
            Root<T> root = criteriaQuery.from(type);

            criteriaQuery.select(root);
            return entityManager.createQuery(criteriaQuery).getResultList().size() > 0;
        } catch (Exception e) {
            throw new com.senla.common.exception.repository.EntityNotFoundException(
                    String.format("Exception while checking entity with id '%s'! %s", e.getMessage())
            );
        }
    }
}
