package com.template.controller;

import com.template.dto.template1.Template1FormDTO;
import com.template.service.template1.Template1Service;
import com.template.service.template2.Template2Service;
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
    private final Template2Service service2;

    @Autowired
    public Template1Controller(Template1Service service, Template2Service service2) {
        this.service = service;
        this.service2 = service2;
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
            Model model
    ){
        model.addAttribute("kode", kode);
        model.addAttribute("detailTemplate1Grid", service.getTemplate1DetailByKode(kode));
        model.addAttribute("template2Grid", service2.getTemplate2ByTemplate1(
                page,
                kode,
                filter1, search1,
                filter2, search2,
                filter3, search3,
                filter4, search4,
                filter5, search5,
                filter6, search6
        ));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service2.getTotalPagesTemplate2ByTemplate1(
                kode,
                filter1, search1,
                filter2, search2,
                filter3, search3,
                filter4, search4,
                filter5, search5,
                filter6, search6
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
