package com.template.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Template_2")
public class Template2 {
    @Id
    @Column(name = "Kode", nullable = false)
    private String kode;

    @Column(name = "Nama", length = 100, nullable = false)
    private String nama;

    @Column(name = "Tanggal", nullable = false)
    private LocalDate tanggal;

    @Column(name = "Harga", nullable = false)
    private BigDecimal harga;

    @Column(name = "Diskon")
    private Double diskon;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @Column(name = "KodeTemplate_1")
    private String kodeTemplate1;

    @ManyToOne
    @JoinColumn(name = "KodeTemplate_1", insertable = false, updatable = false)
    private Template1 template1;
}
