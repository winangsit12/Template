package com.template.service.template1;

import com.template.dto.template1.Template1DetailDTO;
import com.template.dto.template1.Template1FormDTO;
import com.template.dto.template1.Template1IndexDTO;
import com.template.dto.template2.Template2IndexDTO;
import com.template.entity.Template1;
import com.template.entity.Template2;
import com.template.repository.Template1Repository;
import com.template.repository.Template2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Template1ServiceImplementation implements Template1Service{
    private final Template1Repository repository;
    private final int rowInPage = 5;

    @Autowired
    public Template1ServiceImplementation(Template1Repository repository, Template2Repository template2Repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalPages(String filter1, String search1, String filter2, String search2, String filter3, String search3) {
        Map<String, Object> filters = processFilters(filter1, search1, filter2, search2, filter3, search3);
        double totalRecords = repository.getTotalPages((String) filters.get("kode"), (String) filters.get("nama"), (Boolean) filters.get("status"));
        return (int) Math.ceil(totalRecords / rowInPage);
    }

    @Override
    public List<Template1IndexDTO> getAllTemplate1(int page, String filter1, String search1, String filter2, String search2, String filter3, String search3) {
        Map<String, Object> filters = processFilters(filter1, search1, filter2, search2, filter3, search3);
        int totalPages = getTotalPages(filter1, search1, filter2, search2, filter3, search3);
        page = Math.max(1, Math.min(page, totalPages));

        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("kode"));
        List<Template1> template1List = repository.getAllTemplate1(pageable, (String) filters.get("kode"), (String) filters.get("nama"), (Boolean) filters.get("status"));

        return template1List.stream().map(template1 -> new Template1IndexDTO(
                template1.getKode(),
                template1.getNama(),
                template1.getStatus() ? "Aktif" : "Tidak Aktif"
        )).toList();
    }

    private Map<String, Object> processFilters(String... filters) {
        Map<String, Object> filterMap = new HashMap<>();
        for (int i = 0; i < filters.length; i += 2) {
            if (!filters[i].isBlank()) {
                switch (filters[i]) {
                    case "kode" -> filterMap.put("kode", filters[i + 1]);
                    case "nama" -> filterMap.put("nama", filters[i + 1]);
                    case "status" -> filterMap.put("status", Boolean.parseBoolean(filters[i + 1]));
                }
            }
        }
        return filterMap;
    }

    @Override
    public Template1DetailDTO getTemplate1DetailByKode(String kode) {
        Template1DetailDTO template1DetailDTO = new Template1DetailDTO();
        if (!kode.isEmpty()){
            try {
                Template1 template1 = repository.findById(kode).orElseThrow();
                template1DetailDTO.setKode(template1.getKode());
                template1DetailDTO.setNama(template1.getNama());
                template1DetailDTO.setStatus(template1.getStatus() ? "Aktif" : "Tidak Aktif");
            } catch (Exception ignored){}
        }
        return template1DetailDTO;
    }

    @Override
    public Template1FormDTO getTemplate1FormByKode(String kode) {
        Template1FormDTO template1FormDTO = new Template1FormDTO();
        if (!kode.isEmpty()){
            try {
                Template1 template1 = repository.findById(kode).orElseThrow();
                template1FormDTO.setKode(template1.getKode());
                template1FormDTO.setNama(template1.getNama());
                template1FormDTO.setStatus(template1.getStatus());
            } catch (Exception ignored){}
        }
        return template1FormDTO;
    }

    @Override
    public void save(Template1FormDTO template1FormDTO) {
        Template1 template1 = new Template1();
        template1.setKode(template1FormDTO.getKode());
        template1.setNama(template1FormDTO.getNama());
        template1.setStatus(template1FormDTO.getStatus());

        repository.save(template1);
    }

    @Override
    public void delete(String kode) {
        if (!kode.isEmpty()){
            try {
                Template1 template1 =  repository.findById(kode).orElseThrow();
                template1.setDeleteDate(LocalDate.now());
                repository.save(template1);
            } catch (Exception ignored){}
        }
    }
}
