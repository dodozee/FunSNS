package com.funs.pointservice.domain.repository;

import com.funs.pointservice.domain.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Point findByUserId(Long userId);
}
