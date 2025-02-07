package com.template.service;

import com.template.dto.template1.Template1IndexDTO;
import com.template.dto.template2.Template2IndexDTO;

import java.util.List;

public interface Template2Service {
    int getTotalPages(String filter, String earch);
    List<Template2IndexDTO> getAll(int page, String filter, String search);

}
