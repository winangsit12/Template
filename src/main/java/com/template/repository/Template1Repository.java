package com.template.repository;

import com.template.entity.Template1;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Template1Repository extends JpaRepository<Template1, String> {
    @Query("""
            SELECT COUNT(1)
            FROM Template1 t1
            WHERE t1.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT t1
            FROM  Template1 t1
            WHERE t1.deleteDate IS NULL
            """)
    List<Template1> getAllTemplate1(Pageable pageable);

}
