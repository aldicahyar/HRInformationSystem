package com.juaracoding.HRInformationSystem.model;

import javax.persistence.*;
import java.util.Date;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 3/16/2023 20:10
@Last Modified 3/16/2023 20:10
Version 1.0
*/

@Entity
@Table(name = "TrxAbsen")
public class Absen {

    @Id
    @Column(name = "IDAbsen")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAbsen;

    @Column(name = "AbsenIn")
    private Date absenIn;
    @Column(name = "AbsenOut")
    private Date absenOut;

    @ManyToOne
    @JoinColumn(name = "IDUser")
    private Userz userz;

    @Column(name = "IsDelete", nullable = false)
    private Byte isDelete = 1;//khusus disini default 0 karena setelah verifikasi baru di update menjadi 1


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

    public Userz getUserz() {
        return userz;
    }

    public void setUserz(Userz userz) {
        this.userz = userz;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}
