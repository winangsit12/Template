package com.template.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Template_1")
public class Template1 {
    @Id
    @Column(name = "Kode")
    private String kode;

    @Column(name = "Nama", length = 100, nullable = false)
    private String nama;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @OneToMany(mappedBy = "template1")
    private List<Template2> template2List;
}
