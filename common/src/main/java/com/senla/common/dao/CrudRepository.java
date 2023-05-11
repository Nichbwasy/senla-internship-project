package com.senla.common.dao;

import com.senla.common.dao.model.ModelEntity;

import java.util.List;

public interface CrudRepository<T extends ModelEntity<I>, I> {

    T insert(T t);
    T update(T t);
    I delete(I id);
    T select(I id);
    List<T> selectAll();
    Boolean existById(I id);

}
