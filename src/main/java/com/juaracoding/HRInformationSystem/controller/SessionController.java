package com.juaracoding.HRInformationSystem.controller;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 3/5/2023 04:19
@Last Modified 3/5/2023 04:19
Version 1.0
*/


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/session")
public class SessionController {

        @GetMapping()
        public String process(Model model, HttpSession session) {
            @SuppressWarnings("unchecked")
            List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

            if (messages == null) {
                messages = new ArrayList<>();
            }
            model.addAttribute("sessionMessages", messages);

            return "session";
        }

        @PostMapping("/persistMessage")
        public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
            @SuppressWarnings("unchecked")
            List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
            if (messages == null) {
                messages = new ArrayList<>();
                request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
            }
            messages.add(msg);
            request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
            return "redirect:/";
        }

        @PostMapping("/destroy")
        public String destroySession(HttpServletRequest request) {
            request.getSession().invalidate();
            return "redirect:/";
        }
}
