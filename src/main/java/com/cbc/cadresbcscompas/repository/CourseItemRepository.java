package com.cbc.cadresbcscompas.repository;

import com.cbc.cadresbcscompas.model.CourseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseItemRepository extends JpaRepository<CourseItem, Long> {
}
