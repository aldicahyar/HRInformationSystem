package com.juaracoding.HRInformationSystem.repo;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 3/16/2023 20:20
@Last Modified 3/16/2023 20:20
Version 1.0
*/


import com.juaracoding.HRInformationSystem.model.Absen;

import com.juaracoding.HRInformationSystem.model.Akses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenRepo extends JpaRepository<Absen,Long> {
    Page<Absen> findByIsDelete(Pageable page , byte byteIsDelete);
    List<Absen> findByIsDelete(byte byteIsDelete);

    List<Absen> findByAbsenOutAndUserzIdUser(String absenz, Long idUser);

}
