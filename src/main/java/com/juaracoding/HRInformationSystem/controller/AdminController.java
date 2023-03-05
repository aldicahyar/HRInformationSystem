package com.juaracoding.HRInformationSystem.controller;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 3/4/2023 09:24
@Last Modified 3/4/2023 09:24
Version 1.0
*/

import com.juaracoding.HRInformationSystem.model.Userz;
import com.juaracoding.HRInformationSystem.service.UserService;
import com.juaracoding.HRInformationSystem.utils.MappingAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    private Map<String, Object> objectMapper = new HashMap<String, Object>();

    private MappingAttribute mappingAttribute = new MappingAttribute();


    @PostMapping("/dashboard")
    public String dashboard(@ModelAttribute("usr")
                            @Valid Userz userz,
                            BindingResult bindingResult,
                            Model model,
                            WebRequest request,
                            HttpServletRequest hrequest) {

        if (bindingResult.hasErrors()) {
            return "auth/signin";
        }

        objectMapper = userService.doLogin(userz, request);
        Boolean isSuccess = (Boolean) objectMapper.get("success");
        Userz nextUser = (Userz) objectMapper.get("data");

        if (isSuccess) {
            //        System.out.println(WebRequest.SCOPE_REQUEST);//0
            //        System.out.println(WebRequest.SCOPE_SESSION);//1
            //0 = scope request artinya hanya saat login saja tidak menyimpan di memory server / database
            //1 = scope session artinya setelah login dan akan menyimpan data selama session masih aktif
            request.setAttribute("USR_ID", nextUser.getIdUser(), 1);//cara ambil request.getAttribute("USR_ID",1)
            request.setAttribute("EMAIL", nextUser.getEmail(), 1);//cara ambil request.getAttribute("EMAIL",1)
            request.setAttribute("NO_HP", nextUser.getNoHP(), 1);//cara ambil request.getAttribute("NO_HP",1)
            request.setAttribute("USR_NAME", nextUser.getUsername(), 1);//cara ambil request.getAttribute("USR_NAME",1)
            mappingAttribute.setAttribute(model, objectMapper, request);//urutan nya ini terakhir
            return "admin/dashboard";
        } else {
            mappingAttribute.setErrorMessage(bindingResult, objectMapper.get("message").toString());
            return "auth/signin";
        }
    }
}
