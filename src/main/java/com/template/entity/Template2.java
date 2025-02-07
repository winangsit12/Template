package com.template.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Template_2")
public class Template2 {
    @Id
    @Column(name = "Kode")
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

    @ManyToOne
    @JoinColumn(name = "KodeTemplate_1", insertable = false, updatable = false)
    private Template1 template1;

}
