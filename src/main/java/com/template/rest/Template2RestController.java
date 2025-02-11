package com.template.rest;

import com.template.dto.FilterOptionDTO;
import com.template.service.template2.Template2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/template2")
public class Template2RestController {
    private final Template2Service service;

    @Autowired
    public Template2RestController(Template2Service service) {
        this.service = service;
    }

    @GetMapping("kodeTemplate1-options")
    public ResponseEntity<Object> getTemplate1(){
        try{
            List<FilterOptionDTO> optionTemplate1= service.getAllTemplate1Option();
            return ResponseEntity.status(HttpStatus.OK).body(optionTemplate1);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }
}
