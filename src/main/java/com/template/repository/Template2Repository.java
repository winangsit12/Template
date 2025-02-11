package com.template.repository;

import com.template.entity.Template1;
import com.template.entity.Template2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface Template2Repository extends JpaRepository<Template2, String> {
    @Query("""
            SELECT COUNT(1)
            FROM Template2 tmp2
            WHERE tmp2.deleteDate IS NULL
            AND (:kodeTemplate1 IS NULL OR tmp2.kodeTemplate1 = :kodeTemplate1)
            AND (:kode IS NULL OR tmp2.kode = :kode)
            AND (:nama IS NULL OR tmp2.nama = :nama)
            AND (:tanggal IS NULL OR tmp2.tanggal = :tanggal)
            AND (:harga IS NULL OR tmp2.harga = :harga)
            AND (:diskon IS NULL OR tmp2.diskon = :diskon)
            AND (:status IS NULL OR tmp2.status = :status)
            """)
    long getTotalPages(
            String kodeTemplate1,
            String kode,
            String nama,
            LocalDate tanggal,
            BigDecimal harga,
            Double diskon,
            Boolean status
    );


    @Query("""
            SELECT tmp2
            FROM Template2 tmp2
            WHERE tmp2.deleteDate IS NULL
            AND (:kodeTemplate1 IS NULL OR tmp2.kodeTemplate1 = :kodeTemplate1)
            AND (:kode IS NULL OR tmp2.kode = :kode)
            AND (:nama IS NULL OR tmp2.nama = :nama)
            AND (:tanggal IS NULL OR tmp2.tanggal = :tanggal)
            AND (:harga IS NULL OR tmp2.harga = :harga)
            AND (:diskon IS NULL OR tmp2.diskon = :diskon)
            AND (:status IS NULL OR tmp2.status = :status)
            """)
    List<Template2> getAllTemplate2(
            Pageable pageable,
            String kodeTemplate1,
            String kode,
            String nama,
            LocalDate tanggal,
            BigDecimal harga,
            Double diskon,
            Boolean status
    );
}
