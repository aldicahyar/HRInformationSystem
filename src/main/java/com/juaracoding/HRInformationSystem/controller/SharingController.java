package com.juaracoding.HRInformationSystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sharing")
public class SharingController {

    @GetMapping("/ifcondition")
    public String conditionLogic(WebRequest request, Model model) {

        request.setAttribute("MY_SESSION","PAUL",1);
        List<String> list = new ArrayList<String>();
        list.add("CONTENT-0");
        list.add("CONTENT-1");
        list.add("CONTENT-2");
        model.addAttribute("paramOne",null);
        model.addAttribute("paramTwo",null);
        model.addAttribute("ifCondition",null);
        model.addAttribute("unlessCondition",null);
        model.addAttribute("listContentz",list);
        model.addAttribute("userName",null);
        System.out.println(request.getAttribute("USR_ID",1));
        return "test_aja";
    }
}