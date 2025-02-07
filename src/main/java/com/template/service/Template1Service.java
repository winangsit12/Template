package com.template.service;


import com.template.dto.template1.Template1IndexDTO;

import java.util.List;

public interface Template1Service {
    int getTotalPages(String filter, String earch);
    List<Template1IndexDTO> getAll(int page, String filter, String search);

}
