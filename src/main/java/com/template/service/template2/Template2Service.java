package com.template.service.template2;

import com.template.dto.FilterOptionDTO;
import com.template.dto.template2.Template2DetailDTO;
import com.template.dto.template2.Template2FormDTO;
import com.template.dto.template2.Template2IndexDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Template2Service {
    int getTotalPages(String... filters);
    List<Template2IndexDTO> getAllTemplate2(int page, String... filters);
    Template2DetailDTO getTemplate2DetailByKode(String kode);
    Template2FormDTO getTemplate2FormByKode(String kode);
    void save(Template2FormDTO template2FormDTO);
    void delete(String kode);

    int getTotalPagesTemplate2ByTemplate1(String kode, String...  filters);
    List<Template2IndexDTO> getTemplate2ByTemplate1(int page, String kode, String... filters);

    Page<FilterOptionDTO> getAllTemplate1Option(Pageable pageable);
}
