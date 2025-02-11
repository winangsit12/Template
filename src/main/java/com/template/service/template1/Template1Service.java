package com.template.service.template1;

import com.template.dto.template1.Template1DetailDTO;
import com.template.dto.template1.Template1FormDTO;
import com.template.dto.template1.Template1IndexDTO;

import java.util.List;

public interface Template1Service {
    int getTotalPages(
            String filter1, String search1,
            String filter2, String search2,
            String filter3, String search3
    );
    List<Template1IndexDTO> getAllTemplate1(
            int page,
            String filter1, String search1,
            String filter2, String search2,
            String filter3, String search3
    );
    Template1DetailDTO getTemplate1DetailByKode(String kode);
    Template1FormDTO getTemplate1FormByKode(String kode);
    void save(Template1FormDTO template1FormDTO);
    void delete(String kode);
}
