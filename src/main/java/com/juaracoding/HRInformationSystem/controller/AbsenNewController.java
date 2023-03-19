package com.juaracoding.HRInformationSystem.controller;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 3/19/2023 11:24
@Last Modified 3/19/2023 11:24
Version 1.0
*/

import com.juaracoding.HRInformationSystem.configuration.OtherConfig;
import com.juaracoding.HRInformationSystem.dto.AbsenDTO;
import com.juaracoding.HRInformationSystem.dto.UserDTO;
import com.juaracoding.HRInformationSystem.model.Absen;
import com.juaracoding.HRInformationSystem.repo.AbsenRepo;
import com.juaracoding.HRInformationSystem.utils.MappingAttribute;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/absenmgmnt")

public class AbsenNewController {


        @Autowired
        private AbsenRepo absenRepo;
        @Autowired
        private ModelMapper modelMapper;
        private SimpleDateFormat sdf = new SimpleDateFormat("EEEEE dd-MMMM-yy HH:mm:ss");
        private MappingAttribute mappingAttribute = new MappingAttribute();
        @PostMapping("/v1/absen/in")
        public String  absenIn(@ModelAttribute(value = "absen")
                               @Valid AbsenDTO absenDTO
                , BindingResult bindingResult
                , Model model
                , WebRequest request
        )
        {
            if(OtherConfig.getFlagSessionValidation().equals("y"))
            {
                if(request.getAttribute("USR_ID",1)==null){
                    return "redirect:/api/check/logout";
                }
            }
            mappingAttribute.setAttribute(model,request);//untuk set session ke attribut
            Long idUserzz = Long.parseLong(request.getAttribute("USR_ID",1).toString());

            List<Absen> listAbsen = absenRepo.findByAbsenOutAndUserzIdUser(null,idUserzz);
            if(listAbsen.size()==0)
            {
                UserDTO userDTO = new UserDTO();
                userDTO.setIdUser(idUserzz);
                absenDTO.setUserz(userDTO);
                absenDTO.setAbsenIn(new Date());
                Absen absen = modelMapper.map(absenDTO, new TypeToken<Absen>() {}.getType());
                absenRepo.save(absen);
            }
//        model.addAttribute("absen",new AbsenDTO());
//        model.addAttribute("flag","OUT");
            return "admin/dashboard";
        }

        @PostMapping("/v1/absen/out")
        public String absenOut(@ModelAttribute(value = "absen")
                               @Valid AbsenDTO absenDTO
                , BindingResult bindingResult
                , Model model
                , WebRequest request
        )
        {
            if(OtherConfig.getFlagSessionValidation().equals("y"))
            {
                if(request.getAttribute("USR_ID",1)==null){
                    return "redirect:/api/check/logout";
                }
            }
            mappingAttribute.setAttribute(model,request);//untuk set session ke attribut
//        Long selisihAbsenMasuk
//        if(selisihAbsenMasuk <0)
//        {
//            absen.performace(1)//point 100
//        }
//        else if(selisihAbsenMasuk<10)
//        {
//            absen.performace(2)//point 90
//        }
//        else if(selisihAbsenMasuk<30 >10)
//        {
//            absen.performace(3)//60
//        }
//        else if(selisihAbsenMasuk>30)
//        {
//            absen.performace(4)//10
//        }

            Long idUserzz = Long.parseLong(request.getAttribute("USR_ID",1).toString());

            List<Absen> listAbsen = absenRepo.findByAbsenOutAndUserzIdUser(null,idUserzz);
            Absen absen = listAbsen.get(0);
            absen.setAbsenOut(new Date());
            absenRepo.save(absen);

//        model.addAttribute("absen",new AbsenDTO());
//        model.addAttribute("flag","IN");
            return "admin/dashboard";
        }

        @GetMapping("/v1/absen/default")
        public String getAbsensi(Model model, WebRequest request)
        {
            if(OtherConfig.getFlagSessionValidation().equals("y"))
            {
                if(request.getAttribute("USR_ID",1)==null){
                    return "redirect:/api/check/logout";
                }
            }

            mappingAttribute.setAttribute(model,request);//untuk set session ke attribut


            Long idUserzz = Long.parseLong(request.getAttribute("USR_ID",1).toString());
            List<Absen> listAbsen = absenRepo.findByAbsenOutAndUserzIdUser(null,idUserzz);
            String flag = "OUT";
            if(listAbsen.size()==0)
            {
                flag = "IN";
            }
            model.addAttribute("absen",new AbsenDTO());
            model.addAttribute("flag",flag);

            return "absen/absen_new";
        }
}

