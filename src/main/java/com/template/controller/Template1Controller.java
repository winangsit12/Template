package com.template.controller;

import com.template.dto.template1.Template1FormDTO;
import com.template.service.template1.Template1Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("template1")
public class Template1Controller {
    private final Template1Service service;

    @Autowired
    public Template1Controller(Template1Service service) {
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
            Model model
    ){
        model.addAttribute("template1Grid", service.getAllTemplate1(
                page,
                filter1, search1,
                filter2, search2,
                filter3, search3
        ));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(
                filter1, search1,
                filter2, search2,
                filter3, search3
        ));
        model.addAttribute("filter1", filter1);
        model.addAttribute("search1", search1);
        model.addAttribute("filter2", filter2);
        model.addAttribute("search2", search2);
        model.addAttribute("filter3", filter3);
        model.addAttribute("search3", search3);

        return "template1/template1-index";
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam String kode,
            Model model
    ){
        model.addAttribute("detailTemplate1Grid", service.getTemplate1DetailByKode(kode));

        return "template1/template1-detail";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(defaultValue = "") String kode,
            Model model
    ){
        model.addAttribute("formTemplate1Grid", service.getTemplate1FormByKode(kode));

        return "template1/template1-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute
            Template1FormDTO template1FormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "template1/template1-form";
        } else {
            service.save(template1FormDTO);
            return "redirect:/template1";
        }
    }

    @GetMapping("delete")
    public String delete(
            @RequestParam String kode
    ){
        service.delete(kode);
        return "redirect:/template1";
    }
}
