package com.template.repository;

import com.template.entity.Template1;
import com.template.entity.Template2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Template2Repository extends JpaRepository<Template2, String> {
    @Query("""
            SELECT COUNT(1)
            FROM Template2 t2
            WHERE t2.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT t2
            FROM  Template2 t2
            WHERE t2.deleteDate IS NULL
            """)
    List<Template2> getAllTemplate2(Pageable pageable);
}
