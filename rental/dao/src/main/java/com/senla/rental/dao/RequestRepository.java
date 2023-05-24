package com.senla.rental.dao;

import com.senla.rental.model.Request;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @EntityGraph("graph.request.fetches")
    List<Request> findAllByCarId(Long carId);
    @EntityGraph(attributePaths = {"requestRejection", "requestStatus"})
    List<Request> findAllByUserId(Long userId);
}
