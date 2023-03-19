package com.juaracoding.HRInformationSystem.service;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 3/16/2023 22:47
@Last Modified 3/16/2023 22:47
Version 1.0
*/


import com.juaracoding.HRInformationSystem.configuration.OtherConfig;
import com.juaracoding.HRInformationSystem.dto.AbsenDTO;
import com.juaracoding.HRInformationSystem.dto.UserDTO;
import com.juaracoding.HRInformationSystem.handler.ResponseHandler;
import com.juaracoding.HRInformationSystem.model.Absen;
import com.juaracoding.HRInformationSystem.repo.AbsenRepo;
import com.juaracoding.HRInformationSystem.utils.ConstantMessage;
import com.juaracoding.HRInformationSystem.utils.LoggingFile;
import com.juaracoding.HRInformationSystem.utils.TransformToDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

/*
    KODE MODUL 03
 */
@Service
@Transactional
public class AbsenService{

    private AbsenRepo absenRepo;

    private String[] strExceptionArr = new String[2];
    @Autowired
    private ModelMapper modelMapper;

    private Map<String,Object> objectMapper = new HashMap<String,Object>();

    private TransformToDTO transformToDTO = new TransformToDTO();

    private Map<String,String> mapColumnSearch = new HashMap<String,String>();
    private Map<Integer, Integer> mapItemPerPage = new HashMap<Integer, Integer>();
    private String [] strColumnSearch = new String[2];


    @Autowired
    public AbsenService(AbsenRepo absenRepo) {
        mapColumn();
        strExceptionArr[0] = "AbsenService";
        this.absenRepo = absenRepo;
    }

    private void mapColumn()
    {
        mapColumnSearch.put("idabsen","idabsen");
        mapColumnSearch.put("absenIn","absenIn");
        mapColumnSearch.put("absenOut","absenOut");
//        mapColumnSearch.put("point","END POINT");
    }

    public Map<String, Object> saveAbsen(Absen absen, WebRequest request) {
        String strMessage = ConstantMessage.SUCCESS_SAVE;
        Object strUserIdz = request.getAttribute("USR_ID",1);
        try {

            if(strUserIdz==null)
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
                        HttpStatus.NOT_ACCEPTABLE,null,"FV03001",request);
            }

            absen.setUserz(absen.getUserz());
            absen.setAbsenIn(new Date());
            absenRepo.save(absen);
        } catch (Exception e) {
            strExceptionArr[1] = "saveMenu(Menu menu, WebRequest request) --- LINE 67";
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
                    HttpStatus.BAD_REQUEST,
                    transformToDTO.transformObjectDataEmpty(objectMapper,mapColumnSearch),
                    "FE03001", request);
        }
        return new ResponseHandler().generateModelAttribut(strMessage,
                HttpStatus.CREATED,
                transformToDTO.transformObjectDataSave(objectMapper, absen.getIdAbsen(),mapColumnSearch),
                null, request);
    }

    public Map<String, Object> updateAbsen(Long idAbsen,Absen absen, WebRequest request) {
        String strMessage = ConstantMessage.SUCCESS_UPDATE;
        Object strUserIdz = request.getAttribute("USR_ID",1);

        try {
            Absen nextAbsen = absenRepo.findById(idAbsen).orElseThrow(
                    ()->null
            );

            if(nextAbsen==null)
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.WARNING_MENU_NOT_EXISTS,
                        HttpStatus.NOT_ACCEPTABLE,
                        transformToDTO.transformObjectDataEmpty(objectMapper,mapColumnSearch),
                        "FV03002",request);
            }
            if(strUserIdz==null)
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
                        HttpStatus.NOT_ACCEPTABLE,
                        null,
                        "FV03003",request);
            }
//            nextAbsen.setAbsenIn(absen.getAbsenIn());
//            nextAbsen.setAbsenOut(absen.getAbsenOut());
//            nextAbsen.setAbsenOut(new Date());
            nextAbsen.setUserz(absen.getUserz());
//            nextAbsen.setModifiedBy(Integer.parseInt(strUserIdz.toString()));
            nextAbsen.setAbsenOut(new Date());

        } catch (Exception e) {
            strExceptionArr[1] = "updateMenu(Long idMenu,Menu menu, WebRequest request) --- LINE 92";
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
                    HttpStatus.BAD_REQUEST,
                    transformToDTO.transformObjectDataEmpty(objectMapper,mapColumnSearch),
                    "FE03002", request);
        }
        return new ResponseHandler().generateModelAttribut(strMessage,
                HttpStatus.CREATED,
                transformToDTO.transformObjectDataEmpty(objectMapper,mapColumnSearch),
                null, request);
    }



//    @Transactional(rollbackFor = Exception.class)
//    public Map<String, Object> saveUploadFileMenu(List<Menu> listMenu,
//                                                  MultipartFile multipartFile,
//                                                  WebRequest request) throws Exception {
//        List<Menu> listMenuResult = null;
//        String strMessage = ConstantMessage.SUCCESS_SAVE;
//
//        try {
//            listMenuResult = menuRepo.saveAll(listMenu);
//            if (listMenuResult.size() == 0) {
//                strExceptionArr[1] = "saveUploadFile(List<Menu> listMenu, MultipartFile multipartFile, WebRequest request) --- LINE 82";
//                LoggingFile.exceptionStringz(strExceptionArr, new ResourceNotFoundException("FILE KOSONG"), OtherConfig.getFlagLogging());
//                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_EMPTY_FILE + " -- " + multipartFile.getOriginalFilename(),
//                        HttpStatus.BAD_REQUEST, null, "FV03004", request);
//            }
//        } catch (Exception e) {
//            strExceptionArr[1] = "saveUploadFile(List<Menu> listMenu, MultipartFile multipartFile, WebRequest request) --- LINE 88";
//            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
//            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
//                    HttpStatus.BAD_REQUEST, null, "FE03002", request);
//        }
//        return new ResponseHandler().
//                generateModelAttribut(strMessage,
//                        HttpStatus.CREATED,
//                        transformToDTO.transformObjectDataEmpty(objectMapper,mapColumnSearch),
//                        null,
//                        request);
//    }

    public Map<String,Object> findAllAbsen(Pageable pageable, WebRequest request)
    {
        List<AbsenDTO> listAbsenDTO = null;
        Map<String,Object> mapResult = null;
        Page<Absen> pageAbsen = null;
        List<Absen> listAbsen = null;

        try
        {
            pageAbsen = absenRepo.findByIsDelete(pageable,(byte)1);
            listAbsen = pageAbsen.getContent();
            if(listAbsen.size()==0)
            {
                return new ResponseHandler().
                        generateModelAttribut(ConstantMessage.WARNING_DATA_EMPTY,
                                HttpStatus.OK,
                                transformToDTO.transformObjectDataEmpty(objectMapper,pageable,mapColumnSearch),//HANDLE NILAI PENCARIAN
                                "FV03005",
                                request);
            }
            listAbsenDTO = modelMapper.map(listAbsen, new TypeToken<List<AbsenDTO>>() {}.getType());
            mapResult = transformToDTO.transformObject(objectMapper,listAbsenDTO,pageAbsen,mapColumnSearch);

        }
        catch (Exception e)
        {
            strExceptionArr[1] = "findAllMenu(Pageable pageable, WebRequest request) --- LINE 178";
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_INTERNAL_SERVER,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    transformToDTO.transformObjectDataEmpty(objectMapper,pageable,mapColumnSearch),//HANDLE NILAI PENCARIAN
                    "FE03003", request);
        }

        return new ResponseHandler().
                generateModelAttribut(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        null);
    }

    public Map<String,Object> findByPage(Pageable pageable,WebRequest request,String columFirst,String valueFirst)
    {
        Page<Absen> pageAbsen = null;
        List<Absen> listAbsen = null;
        List<AbsenDTO> listAbsenDTO = null;
        Map<String,Object> mapResult = null;

        try
        {
            if(columFirst.equals("id"))
            {
                try
                {
                    Long.parseLong(valueFirst);
                }
                catch (Exception e)
                {
                    strExceptionArr[1] = "findByPage(Pageable pageable,WebRequest request,String columFirst,String valueFirst) --- LINE 209";
                    LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
                    return new ResponseHandler().
                            generateModelAttribut(ConstantMessage.WARNING_DATA_EMPTY,
                                    HttpStatus.OK,
                                    transformToDTO.transformObjectDataEmpty(objectMapper,pageable,mapColumnSearch),//HANDLE NILAI PENCARIAN
                                    "FE03004",
                                    request);
                }
            }
            pageAbsen = getDataByValue(pageable,columFirst,valueFirst);
            listAbsen = pageAbsen.getContent();
            if(listAbsen.size()==0)
            {
                return new ResponseHandler().
                        generateModelAttribut(ConstantMessage.WARNING_DATA_EMPTY,
                                HttpStatus.OK,
                                transformToDTO.transformObjectDataEmpty(objectMapper,pageable,mapColumnSearch),//HANDLE NILAI PENCARIAN EMPTY
                                "FV03006",
                                request);
            }
            listAbsenDTO = modelMapper.map(listAbsen, new TypeToken<List<AbsenDTO>>() {}.getType());
            mapResult = transformToDTO.transformObject(objectMapper,listAbsenDTO,pageAbsen,mapColumnSearch);
            System.out.println("LIST DATA => "+listAbsenDTO.size());
        }

        catch (Exception e)
        {
            strExceptionArr[1] = "findByPage(Pageable pageable,WebRequest request,String columFirst,String valueFirst) --- LINE 237";
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    transformToDTO.transformObjectDataEmpty(objectMapper,pageable,mapColumnSearch),
                    "FE03005", request);
        }
        return new ResponseHandler().
                generateModelAttribut(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        request);
    }

    public Map<String,Object> findById(Long id, WebRequest request)
    {
        Absen absen = absenRepo.findById(id).orElseThrow (
                ()-> null
        );
        if(absen == null)
        {
            return new ResponseHandler().generateModelAttribut(ConstantMessage.WARNING_MENU_NOT_EXISTS,
                    HttpStatus.NOT_ACCEPTABLE,
                    transformToDTO.transformObjectDataEmpty(objectMapper,mapColumnSearch),
                    "FV03005",request);
        }
        AbsenDTO absenDTO = modelMapper.map(absen, new TypeToken<AbsenDTO>() {}.getType());
        return new ResponseHandler().
                generateModelAttribut(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        absenDTO,
                        null,
                        request);
    }


    public List<AbsenDTO> getAllAbsen()//KHUSUS UNTUK FORM INPUT SAJA
    {
        List<AbsenDTO> listAbsenDTO = null;
        Map<String,Object> mapResult = null;
        List<Absen> listAbsen = null;

        try
        {
            listAbsen = absenRepo.findByIsDelete((byte)1);
            if(listAbsen.size()==0)
            {
                return new ArrayList<AbsenDTO>();
            }
            listAbsenDTO = modelMapper.map(listAbsen, new TypeToken<List<AbsenDTO>>() {}.getType());
        }
        catch (Exception e)
        {
            strExceptionArr[1] = "findAllMenu() --- LINE 304";
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
            return listAbsenDTO;
        }
        return listAbsenDTO;
    }



    private Page<Absen> getDataByValue(Pageable pageable, String paramColumn, String paramValue)
    {
        if(paramValue.equals(""))
        {
            return absenRepo.findByIsDelete(pageable,(byte) 1);
        }
//        if(paramColumn.equals("id"))
//        {
//            return menuRepo.findByIsDeleteAndIdMenu(pageable,(byte) 1,Long.parseLong(paramValue));
//        } else if (paramColumn.equals("nama")) {
//            return menuRepo.findByIsDeleteAndNamaMenuContainsIgnoreCase(pageable,(byte) 1,paramValue);
//        } else if (paramColumn.equals("path")) {
//            return menuRepo.findByIsDeleteAndPathMenuContainsIgnoreCase(pageable,(byte) 1,paramValue);
//        } else if (paramColumn.equals("point")) {
//            return menuRepo.findByIsDeleteAndEndPointContainsIgnoreCase(pageable,(byte) 1,paramValue);
//        }

        return absenRepo.findByIsDelete(pageable,(byte) 1);
    }

}
