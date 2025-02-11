package com.template.service.template2;

import com.template.dto.FilterOptionDTO;
import com.template.dto.template2.Template2DetailDTO;
import com.template.dto.template2.Template2FormDTO;
import com.template.dto.template2.Template2IndexDTO;
import com.template.entity.Template1;
import com.template.entity.Template2;
import com.template.helper.CurrencyHelper;
import com.template.helper.DateHelper;
import com.template.helper.PercentHelper;
import com.template.repository.Template1Repository;
import com.template.repository.Template2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class Template2ServiceImplementation implements Template2Service{
    private final Template2Repository repository;
    private final Template1Repository template1Repository;
    private final int rowInPage = 5;

    @Autowired
    public Template2ServiceImplementation(Template2Repository repository, Template1Repository template1Repository) {
        this.repository = repository;
        this.template1Repository = template1Repository;
    }

    @Override
    public int getTotalPages(String... filters) {
        Map<String, Object> filterMap = processFilters(filters);
        double totalRecords = repository.getTotalPages(
                (String) filterMap.get("kodeTemplate1"),
                (String) filterMap.get("kode"),
                (String) filterMap.get("nama"),
                (LocalDate) filterMap.get("tanggal"),
                (BigDecimal) filterMap.get("harga"),
                (Double) filterMap.get("diskon"),
                (Boolean) filterMap.get("status")
        );
        return (int) Math.ceil(totalRecords / rowInPage);
    }

    @Override
    public List<Template2IndexDTO> getAllTemplate2(int page, String... filters) {
        Map<String, Object> filterMap = processFilters(filters);
        int totalPages = getTotalPages(filters);
        page = Math.max(1, Math.min(page, totalPages));

        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("kode"));
        List<Template2> template2List = repository.getAllTemplate2(
                pageable,
                (String) filterMap.get("kodeTemplate1"),
                (String) filterMap.get("kode"),
                (String) filterMap.get("nama"),
                (LocalDate) filterMap.get("tanggal"),
                (BigDecimal) filterMap.get("harga"),
                (Double) filterMap.get("diskon"),
                (Boolean) filterMap.get("status")
        );

        return template2List.stream().map(template2 -> {
            Template2IndexDTO dto = new Template2IndexDTO();
            dto.setKodeTemplate1(String.format("%s - %s", template2.getKodeTemplate1(), template2.getTemplate1().getNama()));
            dto.setKode(template2.getKode());
            dto.setNama(template2.getNama());
            dto.setTanggal(DateHelper.dateParse(template2.getTanggal(), "dd MMMM yyyy"));
            dto.setHarga(CurrencyHelper.currencyParse(template2.getHarga()));
            dto.setDiskon(PercentHelper.percentageParse(template2.getDiskon()));
            dto.setStatus(template2.getStatus() ? "Aktif" : "Tidak Aktif");
            return dto;
        }).toList();
    }

    private Map<String, Object> processFilters(String... filters) {
        Map<String, Object> filterMap = new HashMap<>();

        for (int i = 0; i < filters.length - 1; i += 2) {
            String key = filters[i];
            String value = filters[i + 1];

            if (key != null && !key.isBlank() && value != null && !value.isBlank()) {
                switch (key) {
                    case "kodeTemplate1", "kode", "nama" -> filterMap.put(key, value);
                    case "tanggal" -> {
                        try {
                            filterMap.put(key, LocalDate.parse(value));
                        } catch (Exception e) {
                            System.out.println("Invalid date format: " + value);
                        }
                    }
                    case "harga" -> {
                        try {
                            filterMap.put(key, new BigDecimal(value));
                        } catch (Exception e) {
                            System.out.println("Invalid harga format: " + value);
                        }
                    }
                    case "diskon" -> {
                        try {
                            filterMap.put(key, Double.parseDouble(value) / 100);
                        } catch (Exception e) {
                            System.out.println("Invalid diskon format: " + value);
                        }
                    }
                    case "status" -> filterMap.put(key, Boolean.parseBoolean(value));
                }
            }
        }
        return filterMap;
    }

    @Override
    public Template2DetailDTO getTemplate2DetailByKode(String kode) {
        Template2DetailDTO template2DetailDTO = new Template2DetailDTO();
        if (!kode.isEmpty()){
            try {
                Template2 template2 = repository.findById(kode).orElseThrow();
                template2DetailDTO.setKodeTemplate1(String.format("%s - %s", template2.getKodeTemplate1(), template2.getTemplate1().getNama()));
                template2DetailDTO.setKode(template2.getKode());
                template2DetailDTO.setNama(template2.getNama());
                template2DetailDTO.setTanggal(DateHelper.dateParse(template2.getTanggal(), "dd MMMM yyyy"));
                template2DetailDTO.setHarga(CurrencyHelper.currencyParse(template2.getHarga()));
                template2DetailDTO.setDiskon(PercentHelper.percentageParse(template2.getDiskon()));
                template2DetailDTO.setStatus(template2.getStatus() ? "Aktif" : "Tidak Aktif");
            } catch (Exception ignored){}
        }
        return template2DetailDTO;
    }

    @Override
    public Template2FormDTO getTemplate2FormByKode(String kode) {
        Template2FormDTO template2FormDTO = new Template2FormDTO();
        if (!kode.isEmpty()){
            try {
                Template2 template2 = repository.findById(kode).orElseThrow();
                template2FormDTO.setKodeTemplate1(String.format("%s - %s", template2.getKodeTemplate1(), template2.getTemplate1().getNama()));
                template2FormDTO.setKode(template2.getKode());
                template2FormDTO.setNama(template2.getNama());
                template2FormDTO.setTanggal(template2.getTanggal());
                template2FormDTO.setHarga(template2.getHarga());
                template2FormDTO.setDiskon(template2.getDiskon() * 100);
                template2FormDTO.setStatus(template2.getStatus());
            } catch (Exception ignored){}
        }
        return template2FormDTO;
    }

    @Override
    public void save(Template2FormDTO template2FormDTO) {
        Template2 template2 = new Template2();
        template2.setKodeTemplate1(template2FormDTO.getKodeTemplate1().substring(0, template2FormDTO.getKodeTemplate1().indexOf(" - ")));
        template2.setKode(template2FormDTO.getKode());
        template2.setNama(template2FormDTO.getNama());
        template2.setTanggal(template2FormDTO.getTanggal());
        template2.setHarga(template2FormDTO.getHarga());
        template2.setDiskon(template2FormDTO.getDiskon() / 100);
        template2.setStatus(template2FormDTO.getStatus());

        repository.save(template2);
    }

    @Override
    public void delete(String kode) {
        if (!kode.isEmpty()){
            try {
                Template2 template2 = repository.findById(kode).orElseThrow();
                template2.setDeleteDate(LocalDate.now());
                repository.save(template2);
            } catch (Exception ignored){}
        }
    }

    @Override
    public int getTotalPagesTemplate2ByTemplate1(String kode, String... filters) {
        Map<String, Object> filterMap = processFilters(filters);
        double totalRecords = repository.getTotalPagesTemplate2ByTemplate1(
                kode,
                (String) filterMap.get("kode"),
                (String) filterMap.get("nama"),
                (LocalDate) filterMap.get("tanggal"),
                (BigDecimal) filterMap.get("harga"),
                (Double) filterMap.get("diskon"),
                (Boolean) filterMap.get("status")
        );
        return (int) Math.ceil(totalRecords / rowInPage);
    }

    @Override
    public List<Template2IndexDTO> getTemplate2ByTemplate1(int page, String kode, String... filters) {
        Map<String, Object> filterMap = processFilters(filters);
        int totalPages = getTotalPages(filters);
        page = Math.max(1, Math.min(page, totalPages));

        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("kode"));
        List<Template2> template2List = repository.getTemplate2ByTemplate1(
                pageable,
                kode,
                (String) filterMap.get("kode"),
                (String) filterMap.get("nama"),
                (LocalDate) filterMap.get("tanggal"),
                (BigDecimal) filterMap.get("harga"),
                (Double) filterMap.get("diskon"),
                (Boolean) filterMap.get("status")
        );

        return template2List.stream().map(template2 -> {
            Template2IndexDTO dto = new Template2IndexDTO();
            dto.setKode(template2.getKode());
            dto.setNama(template2.getNama());
            dto.setTanggal(DateHelper.dateParse(template2.getTanggal(), "dd MMMM yyyy"));
            dto.setHarga(CurrencyHelper.currencyParse(template2.getHarga()));
            dto.setDiskon(PercentHelper.percentageParse(template2.getDiskon()));
            dto.setStatus(template2.getStatus() ? "Aktif" : "Tidak Aktif");
            return dto;
        }).toList();
    }

    @Override
    public Page<FilterOptionDTO> getAllTemplate1Option(Pageable pageable) {
        Page<Template1> template1Page = template1Repository.getAllTemplate1Option(pageable);

        return template1Page.map(template1 -> {
            FilterOptionDTO dto = new FilterOptionDTO();
            dto.setKode(template1.getKode());
            dto.setNama(template1.getNama());
            return dto;
        });
    }

}
