package com.template.dto.template1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Template1FormDTO {
    @NotBlank(message = "Kode Template1 tidak boleh kosong")
    private String kode;
    @NotBlank(message = "Nama Template1 tidak boleh kosong")
    @Length(max = 100, message = "Nama Template1 tidak boleh melebihi 100 karakter")
    private String nama;
    @NotNull(message = "Status Template1 tidak boleh kosong")
    private Boolean status;
}
