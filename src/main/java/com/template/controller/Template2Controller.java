package com.template.controller;

import com.template.dto.template2.Template2FormDTO;
import com.template.service.template2.Template2Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("template2")
public class Template2Controller {
    private final Template2Service service;

    @Autowired
    public Template2Controller(Template2Service service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter1,
            @RequestParam(defaultValue = "") String search1,
            @RequestParam(defaultValue = "") String filter2,
            @RequestParam(defaultValue = "") String search2,
            @RequestParam(defaultValue = "") String filter3,
            @RequestParam(defaultValue = "") String search3,
            @RequestParam(defaultValue = "") String filter4,
            @RequestParam(defaultValue = "") String search4,
            @RequestParam(defaultValue = "") String filter5,
            @RequestParam(defaultValue = "") String search5,
            @RequestParam(defaultValue = "") String filter6,
            @RequestParam(defaultValue = "") String search6,
            @RequestParam(defaultValue = "") String filter7,
            @RequestParam(defaultValue = "") String search7,
            Model model
    ){
        model.addAttribute("template2Grid", service.getAllTemplate2(
                page,
                filter1, search1,
                filter2, search2,
                filter3, search3,
                filter4, search4,
                filter5, search5,
                filter6, search6,
                filter7, search7
        ));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(
                filter1, search1,
                filter2, search2,
                filter3, search3,
                filter4, search4,
                filter5, search5,
                filter6, search6,
                filter7, search7
        ));
        model.addAttribute("filter1", filter1);
        model.addAttribute("search1", search1);
        model.addAttribute("filter2", filter2);
        model.addAttribute("search2", search2);
        model.addAttribute("filter3", filter3);
        model.addAttribute("search3", search3);
        model.addAttribute("filter4", filter4);
        model.addAttribute("search4", search4);
        model.addAttribute("filter5", filter5);
        model.addAttribute("search5", search5);
        model.addAttribute("filter6", filter6);
        model.addAttribute("search6", search6);
        model.addAttribute("filter7", filter7);
        model.addAttribute("search7", search7);

        return "template2/template2-index";
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam String kode,
            Model model
    ){
        model.addAttribute("detailTemplate2Grid", service.getTemplate2DetailByKode(kode));

        return "template2/template2-detail";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(defaultValue = "") String kode,
            Model model
    ){
        model.addAttribute("formTemplate2Grid", service.getTemplate2FormByKode(kode));

        return "template2/template2-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute
            Template2FormDTO template2FormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "template2/template2-form";
        } else {
            service.save(template2FormDTO);
            return "redirect:/template2";
        }
    }

    @GetMapping("delete")
    public String delete(
            @RequestParam String kode
    ){
        service.delete(kode);
        return "redirect:/template2";
    }
}
