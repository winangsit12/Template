package com.template.service;

import com.template.dto.template1.Template1IndexDTO;
import com.template.dto.template2.Template2IndexDTO;
import com.template.entity.Template1;
import com.template.entity.Template2;
import com.template.repository.Template2Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class Template2ServiceImplementation implements Template2Service {
    private final Template2Repository repository;
    private final int rowInPage = 4;

    public Template2ServiceImplementation(Template2Repository repository) {
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
    public List<Template2IndexDTO> getAll(int page, String filter, String search) {

        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("kode"));
        List<Template2> template2List = new LinkedList<>();
        if (filter.isEmpty()) {
            template2List = repository.getAllTemplate2(pageable);
        } else {
            switch (filter) {

            }
        }
        List<Template2IndexDTO> template2IndexDTOS = new LinkedList<>();
        for (Template2 template2:
                template2List) {
            Template2IndexDTO template2IndexDTO = new Template2IndexDTO();
            template2IndexDTO.setKode(template2.getKode());
            template2IndexDTO.setNama(template2.getNama());
            template2IndexDTO.setTemplate1(template2.getTemplate1().getNama());

            LocalDate tanggal = template2.getTanggal();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID"));
            String formattedTanggal = tanggal.format(formatter);
            template2IndexDTO.setTanggal(formattedTanggal);

            BigDecimal harga = template2.getHarga();
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String formattedHarga = formatRupiah.format(harga);
            template2IndexDTO.setHarga(formattedHarga);

            template2IndexDTO.setDiskon(template2.getDiskon() + "%");
            String status = !template2.getStatus() ? "Tidak Aktif" : "Aktif";
            template2IndexDTO.setStatus(status);
            template2IndexDTOS.add(template2IndexDTO);
        }
        return template2IndexDTOS;
    }

}
