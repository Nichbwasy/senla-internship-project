package com.senla.rental.dao;

import com.senla.rental.dao.projections.RequestIdsOnly;
import com.senla.rental.model.Request;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Boolean existsByRequestOrderNumber(String orderNumber);
    Request getByRequestOrderNumber(String orderNumber);

    @EntityGraph("graph.request.fetches")
    List<Request> findAllByCarId(Long carId);
    @EntityGraph(attributePaths = {"requestRejection", "requestStatus"})
    List<Request> findAllByUserId(Long userId);
    List<RequestIdsOnly> findAllByStartTimeBetween(Timestamp startTime, Timestamp endTime);
    @Query(value = "select r.id as id, r.car_id as carId, r.user_id as userId from request r where r.start_time between ?1 and ?2", nativeQuery = true)
    List<RequestIdsOnly> findAllByStartTimeBetweenNativeQuery(Timestamp startTime, Timestamp endTime);
}
