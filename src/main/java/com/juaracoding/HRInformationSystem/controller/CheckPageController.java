package com.juaracoding.HRInformationSystem.controller;

import cn.apiclub.captcha.Captcha;
import com.juaracoding.HRInformationSystem.dto.ForgetPasswordDTO;
import com.juaracoding.HRInformationSystem.dto.UserDTO;
import com.juaracoding.HRInformationSystem.model.Userz;
import com.juaracoding.HRInformationSystem.utils.CaptchaUtils;
import com.juaracoding.HRInformationSystem.utils.MappingAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/check")
public class CheckPageController {

    private MappingAttribute mappingAttribute = new MappingAttribute();

    private Map<String, Object> objectMapper = new HashMap<String, Object>();

    @GetMapping("/signin")
    public String pageOne(Model model)
    {
        Captcha captcha = CaptchaUtils.createCaptcha(150, 60);
        Userz users = new Userz();
        users.setHidden(captcha.getAnswer());
        users.setCaptcha("");
        users.setImage(CaptchaUtils.encodeBase64(captcha));
        model.addAttribute("usr",users);
        return "auth/signin";
    }

    @GetMapping("/register")
    public String pageTwo(Model model, WebRequest request)
    {
        UserDTO users = new UserDTO();
        model.addAttribute("usr",users);
        mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
        return "admin/tambah_user";
    }

    @GetMapping("/verify")
    public String pageThree(Model model)
    {
        model.addAttribute("usr",new Userz());
        return "auth/verifikasi";
    }

    @GetMapping("/index1")
    public String pageFour()
    {
        return "index_1";

    }
    @GetMapping("/styledPage")
    public String pageFive(Model model)
    {
        model.addAttribute("name", "Paulo");
        return "styledPage";
    }

    @GetMapping("/index")
    public String pageSix()
    {
        return "index";
    }

    @GetMapping("/forgetpwd")
    public String pageSeven(Model model)
    {
        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        model.addAttribute("forgetpwd",forgetPasswordDTO);
        return "auth/forget_pwd_email";
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

}