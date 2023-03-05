package com.juaracoding.HRInformationSystem.controller;

import cn.apiclub.captcha.Captcha;
import com.juaracoding.HRInformationSystem.model.Userz;
import com.juaracoding.HRInformationSystem.utils.CaptchaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    public String pageTwo(Model model, WebRequest request)
    {
        Captcha captcha = CaptchaUtils.createCaptcha(150, 60);

        Userz users = new Userz();
        users.setHidden(captcha.getAnswer());
        users.setCaptcha("");
        users.setImage(CaptchaUtils.encodeBase64(captcha));
        model.addAttribute("usr",users);
        return "auth/login";
    }
}
