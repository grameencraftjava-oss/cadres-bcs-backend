package com.cbc.cadresbcscompas.repository;

import com.cbc.cadresbcscompas.model.PortalContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortalContentRepository extends JpaRepository<PortalContent, Long> {
    List<PortalContent> findByCategoryOrderByCreatedAtDesc(String category);
}
