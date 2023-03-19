package com.juaracoding.HRInformationSystem.dto;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 3/16/2023 20:18
@Last Modified 3/16/2023 20:18
Version 1.0
*/

import java.util.Date;
import java.util.List;

public class AbsenDTO {


    private Long idAbsen;
    private Date absenIn;
    private Date absenOut;

    private List<UserDTO> listUser;

    public List<UserDTO> getListUser() {
        return listUser;
    }

    public void setListUser(List<UserDTO> listUser) {
        this.listUser = listUser;
    }

    private UserDTO userz;

    public UserDTO getUserz() {
        return userz;
    }

    public void setUserz(UserDTO userz) {
        this.userz = userz;
    }

    public Long getIdAbsen() {
        return idAbsen;
    }

    public void setIdAbsen(Long idAbsen) {
        this.idAbsen = idAbsen;
    }

    public Date getAbsenIn() {
        return absenIn;
    }

    public void setAbsenIn(Date absenIn) {
        this.absenIn = absenIn;
    }

    public Date getAbsenOut() {
        return absenOut;
    }

    public void setAbsenOut(Date absenOut) {
        this.absenOut = absenOut;
    }
}
