package com.template.dto.template2;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Template2FormDTO {
    @NotBlank(message = "Kode Template2 tidak boleh kosong")
    private String kode;
    @NotBlank(message = "Nama Template2 tidak boleh kosong")
    @Length(max = 100, message = "Nama Template2 tidak boleh melebihi 100 karakter")
    private String nama;
    @NotNull(message = "Tanggal Template2 tidak boleh kosong")
    private LocalDate tanggal;
    @NotNull(message = "Harga Template2 tidak boleh kosong")
    private BigDecimal harga;
    @NotNull(message = "Diskon Template2 tidak boleh kosong")
    private Double diskon;
    @NotNull(message = "Status Template2 tidak boleh kosong")
    private Boolean status;
    @NotBlank(message = "Kode Template1 tidak boleh kosong")
    private String kodeTemplate1;
}
