package com.template.rest;

import com.template.dto.FilterOptionDTO;
import com.template.service.template2.Template2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/template2")
public class Template2RestController {
    private final Template2Service service;

    @Autowired
    public Template2RestController(Template2Service service) {
        this.service = service;
    }

    @GetMapping("kodeTemplate1-options")
    public ResponseEntity<Map<String, Object>> getTemplate1(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<FilterOptionDTO> optionTemplate1 = service.getAllTemplate1Option(PageRequest.of(page, size));

        Map<String, Object> response = new HashMap<>();
        response.put("content", optionTemplate1.getContent());
        response.put("page", optionTemplate1.getNumber());
        response.put("size", optionTemplate1.getSize());
        response.put("totalElements", optionTemplate1.getTotalElements());
        response.put("totalPages", optionTemplate1.getTotalPages());

        return ResponseEntity.ok(response);
    }

}
