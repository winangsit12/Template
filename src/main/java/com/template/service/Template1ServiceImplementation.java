package com.template.service;

import com.template.dto.template1.Template1IndexDTO;
import com.template.entity.Template1;
import com.template.repository.Template1Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class Template1ServiceImplementation implements Template1Service{
    private final Template1Repository repository;
    private final int rowInPage = 3;

    public Template1ServiceImplementation(Template1Repository repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalPages(String filter, String earch) {
        double page = 0;
        if (filter.isEmpty()){
            page = repository.getTotalPages();
        }else {

        }
        return (int) Math.ceil(page/rowInPage);
    }

    @Override
    public List<Template1IndexDTO> getAll(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("kode"));
        List<Template1> template1List = new LinkedList<>();
        if (filter.isEmpty()) {
            template1List = repository.getAllTemplate1(pageable);
        } else {
            switch (filter) {

            }
        }
        List<Template1IndexDTO> template1IndexDTOS = new LinkedList<>();
        for (Template1 template1:
             template1List) {
            Template1IndexDTO template1IndexDTO = new Template1IndexDTO();
            template1IndexDTO.setKode(template1.getKode());
            template1IndexDTO.setNama(template1.getNama());
            String status = !template1.getStatus() ? "Tidak Aktif" : "Aktif";
            template1IndexDTO.setStatus(status);
            template1IndexDTOS.add(template1IndexDTO);
        }
        return template1IndexDTOS;
    }
}
