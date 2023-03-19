package com.juaracoding.HRInformationSystem.controller;


import com.juaracoding.HRInformationSystem.configuration.OtherConfig;
import com.juaracoding.HRInformationSystem.dto.AbsenDTO;
import com.juaracoding.HRInformationSystem.dto.MenuDTO;
import com.juaracoding.HRInformationSystem.dto.MenuHeaderDTO;
import com.juaracoding.HRInformationSystem.dto.UserDTO;
import com.juaracoding.HRInformationSystem.model.Absen;
import com.juaracoding.HRInformationSystem.model.Menu;
import com.juaracoding.HRInformationSystem.model.MenuHeader;
import com.juaracoding.HRInformationSystem.service.AbsenService;
import com.juaracoding.HRInformationSystem.service.MenuHeaderService;
import com.juaracoding.HRInformationSystem.service.MenuService;
import com.juaracoding.HRInformationSystem.service.UserService;
import com.juaracoding.HRInformationSystem.utils.ConstantMessage;
import com.juaracoding.HRInformationSystem.utils.ManipulationMap;
import com.juaracoding.HRInformationSystem.utils.MappingAttribute;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/usrmgmnt")
public class AbsenController {

    private AbsenService absenService;

    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private Map<String,Object> objectMapper = new HashMap<String,Object>();
    private Map<String,String> mapSorting = new HashMap<String,String>();

    private List<Menu> lsCPUpload = new ArrayList<Menu>();

    private String [] strExceptionArr = new String[2];

    private MappingAttribute mappingAttribute = new MappingAttribute();

    @Autowired
    public AbsenController(AbsenService absenService, UserService userService) {
        strExceptionArr[0] = "AbsenController";
        mapSorting();
        this.absenService = absenService;
        this.userService = userService;
    }

    @GetMapping("/v1/absen/new")
    public String createAbsen(Model model,WebRequest request)
    {
        if(OtherConfig.getFlagSessionValidation().equals("y"))
        {
            mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
            if(request.getAttribute("USR_ID",1)==null){
                return "redirect:/api/check/logout";
            }
        }
        model.addAttribute("absen", new AbsenDTO());
        model.addAttribute("listUserAbsen", userService.getAllUser());//untuk parent nya
        return "absen/create_absen";
    }

    @GetMapping("/v1/absen/edit/{id}")
    public String editAbsen(Model model,WebRequest request,@PathVariable("id") Long id)
    {
        if(OtherConfig.getFlagSessionValidation().equals("y"))
        {
            mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
            if(request.getAttribute("USR_ID",1)==null){
                return "redirect:/api/check/logout";
            }
        }
        objectMapper = absenService.findById(id,request);
        AbsenDTO absenDTO = (objectMapper.get("data")==null?null:(AbsenDTO) objectMapper.get("data"));
        if((Boolean) objectMapper.get("success"))
        {
            AbsenDTO absenDTOForSelect = (AbsenDTO) objectMapper.get("data");
            model.addAttribute("absen", absenDTO);
            model.addAttribute("listUserAbsen", userService.getAllUser());
//            System.out.println("selectedValues -> "+menuDTOForSelect.getMenuHeader().getIdMenuHeader());
            UserDTO userDTO = (UserDTO) absenDTOForSelect.getListUser();
            String strSelected = userDTO==null?"null":userDTO.getIdUser().toString();
            model.addAttribute("selectedValues", strSelected);
            return "absen/edit_absen";
        }
        else
        {
            model.addAttribute("absen", new AbsenDTO());
            return "redirect:/api/usrmgmnt/v1/absen/default";
        }
    }
    @PostMapping("/v1/absen/new")
    public String newAbsen(@ModelAttribute(value = "absen")
                          @Valid AbsenDTO absenDTO
            , BindingResult bindingResult
            , Model model
            , WebRequest request
    )
    {

        UserDTO userDTO = absenDTO.getUserz();
        String strIdAbsenz = userDTO==null?null:String.valueOf(userDTO.getIdUser());
        if(strIdAbsenz==null || strIdAbsenz.equals("null") || strIdAbsenz.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,"User WAJIB DIPILIH !!");
        }

        if(OtherConfig.getFlagSessionValidation().equals("y"))
        {
            mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
            if(request.getAttribute("USR_ID",1)==null){
                return "redirect:/api/check/logout";
            }
        }

        /* START VALIDATION */
        if(bindingResult.hasErrors())
        {
            model.addAttribute("absen",absenDTO);
            model.addAttribute("status","error");
//            model.addAttribute("listUserAbsen", userService.getAllUser());

            return "absen/create_absen";
        }
        Boolean isValid = true;
        /*
//            NANTI DIBUATKAN REGEX UNTUK VALIDASI FORMAT PATH DAN ENDPOINT
//         */
//        if(!absenDTO.getPathMenu().startsWith("/api/"))
//        {
//            isValid = false;
//            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.WARNING_MENU_PATH_INVALID);
//        }
//        if(!menuDTO.getEndPoint().equals(OtherConfig.getUrlEndPointVerify()))
//        {
//            isValid = false;
//            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.WARNING_MENU_END_POINTS_INVALID);
//        }

        if(!isValid)
        {
            model.addAttribute("absen",absenDTO);
//            model.addAttribute("listUserAbsen", userService.getAllUser());
            return "absen/create_absen";
        }
        /* END OF VALIDATION */


        Absen absen = modelMapper.map(absenDTO, new TypeToken<Absen>() {}.getType());
        objectMapper = absenService.saveAbsen(absen,request);
        if(objectMapper.get("message").toString().equals(ConstantMessage.ERROR_FLOW_INVALID))//AUTO LOGOUT JIKA ADA PESAN INI
        {
            return "redirect:/api/check/logout";
        }

        if((Boolean) objectMapper.get("success"))
        {
            mappingAttribute.setAttribute(model,objectMapper);
            model.addAttribute("message","DATA BERHASIL DISIMPAN");
            Long idDataSave = objectMapper.get("idDataSave")==null?1:Long.parseLong(objectMapper.get("idDataSave").toString());
            return "redirect:/api/usrmgmnt/v1/absen/fbpsb/0/asc/idAbsen?columnFirst=idAbsen&valueFirst="+idDataSave+"&sizeComponent=5";//LANGSUNG DITAMPILKAN FOKUS KE HASIL EDIT USER TADI
        }
        else
        {
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
//            model.addAttribute("listUserAbsen", userService.getAllUser());
            model.addAttribute("absen",new AbsenDTO());
            return "absen/create_absen";
        }
    }

    @PostMapping("/v1/absen/edit/{id}")
    public String editAbsen(@ModelAttribute("absen")
                           @Valid AbsenDTO absenDTO
            , BindingResult bindingResult
            , Model model
            , WebRequest request
            , @PathVariable("id") Long id
    )
    {
        absenDTO.setIdAbsen(id);
        UserDTO userDTO = absenDTO.getUserz();
        String strIdMenuHeaderz = userDTO==null?null:String.valueOf(userDTO.getIdUser());
        if(strIdMenuHeaderz==null || strIdMenuHeaderz.equals("null") || strIdMenuHeaderz.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,"GROUP MENU WAJIB DIPILIH !!");
        }

        /* START VALIDATION */
        if(bindingResult.hasErrors())
        {
            model.addAttribute("absen",absenDTO);
            model.addAttribute("listUserabsen", userService.getAllUser());
            model.addAttribute("selectedValues", "null");
            return "absen/edit_absen";
        }
        Boolean isValid = true;
//        /*
//            NANTI DIBUATKAN REGEX UNTUK VALIDASI FORMAT PATH DAN ENDPOINT
//         */
//        if(!menuDTO.getPathMenu().startsWith("/api/"))
//        {
//            isValid = false;
//            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.WARNING_MENU_PATH_INVALID);
//        }
//        if(!menuDTO.getEndPoint().equals(OtherConfig.getUrlEndPointVerify()))
//        {
//            isValid = false;
//            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.WARNING_MENU_END_POINTS_INVALID);
//        }

        if(!isValid)
        {
            model.addAttribute("absen",absenDTO);
            model.addAttribute("listUserAbsen", userService.getAllUser());
            return "absen/edit_absen";
        }
        /* END OF VALIDATION */

        Absen absen = modelMapper.map(absenDTO, new TypeToken<Absen>() {}.getType());
        objectMapper = absenService.updateAbsen(id,absen,request);
        if(objectMapper.get("message").toString().equals(ConstantMessage.ERROR_FLOW_INVALID))//AUTO LOGOUT JIKA ADA PESAN INI
        {
            return "redirect:/api/check/logout";
        }

        if((Boolean) objectMapper.get("success"))
        {
            mappingAttribute.setAttribute(model,objectMapper);
            model.addAttribute("absen",new AbsenDTO());
            return "redirect:/api/usrmgmnt/v1/absen/fbpsb/0/asc/idAbsen?columnFirst=idAbsen&valueFirst="+id+"&sizeComponent=5";//LANGSUNG DITAMPILKAN FOKUS KE HASIL EDIT USER TADI
        }
        else
        {
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            model.addAttribute("absen",new AbsenDTO());
            model.addAttribute("listUserAbsen", userService.getAllUser());
            return "absen/edit_absen";
        }
    }


    @GetMapping("/v1/absen/default")
    public String getDefaultData(Model model,WebRequest request)
    {
        if(OtherConfig.getFlagSessionValidation().equals("y"))
        {
            mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
            if(request.getAttribute("USR_ID",1)==null){
                return "redirect:/api/check/logout";
            }
        }
        Pageable pageable = PageRequest.of(0,5, Sort.by("idAbsen"));
        objectMapper = absenService.findAllAbsen(pageable,request);
        mappingAttribute.setAttribute(model,objectMapper,request);

        model.addAttribute("absen",new AbsenDTO());
        model.addAttribute("sortBy","idAbsen");
        model.addAttribute("currentPage",1);
        model.addAttribute("asc","asc");
        model.addAttribute("columnFirst","");
        model.addAttribute("valueFirst","");
        model.addAttribute("sizeComponent",5);
        return "/absen/absen";
    }

    @GetMapping("/v1/absen/fbpsb/{page}/{sort}/{sortby}")
    public String findBYAbsen(
            Model model,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy,
            @RequestParam String columnFirst,
            @RequestParam String valueFirst,
            @RequestParam String sizeComponent,
            WebRequest request
    ){
        sortzBy = mapSorting.get(sortzBy);
        sortzBy = sortzBy==null?"idAbsen":sortzBy;
        Pageable pageable = PageRequest.of(pagez==0?pagez:pagez-1,Integer.parseInt(sizeComponent.equals("")?"5":sizeComponent), sortz.equals("asc")?Sort.by(sortzBy):Sort.by(sortzBy).descending());
        objectMapper = absenService.findByPage(pageable,request,columnFirst,valueFirst);
        mappingAttribute.setAttribute(model,objectMapper,request);
        model.addAttribute("absen",new AbsenDTO());
        model.addAttribute("currentPage",pagez==0?1:pagez);
        model.addAttribute("sortBy", ManipulationMap.getKeyFromValue(mapSorting,sortzBy));
        model.addAttribute("columnFirst",columnFirst);
        model.addAttribute("valueFirst",valueFirst);
        model.addAttribute("sizeComponent",sizeComponent);

        return "/absen/absen";
    }

    private void mapSorting()
    {
        mapSorting.put("idabsen","idabsen");
        mapSorting.put("absenIn","createdDate");
        mapSorting.put("absenOut","modifiedDate");
//        mapSorting.put("endPoint","endPoint");
    }

}