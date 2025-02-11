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
            FROM Template1 tmp1
            WHERE tmp1.deleteDate IS NULL
                AND (:kode IS NULL OR tmp1.kode = :kode)
                AND (:nama IS NULL OR tmp1.nama = :nama)
                AND (:status IS NULL OR tmp1.status = :status)
            """)
    long getTotalPages(
            String kode,
            String nama,
            Boolean status
    );


    @Query("""
            SELECT tmp1
            FROM Template1 tmp1
            WHERE tmp1.deleteDate IS NULL
                AND (:kode IS NULL OR tmp1.kode = :kode)
                AND (:nama IS NULL OR tmp1.nama = :nama)
                AND (:status IS NULL OR tmp1.status = :status)
            """)
    List<Template1> getAllTemplate1(
            Pageable pageable,
            String kode,
            String nama,
            Boolean status
    );

    @Query("""
            SELECT tmp1
            FROM Template1 tmp1
            WHERE tmp1.deleteDate IS NULL
            AND tmp1.status = true
            """)
    List<Template1> getAllTemplate1Option();
}
