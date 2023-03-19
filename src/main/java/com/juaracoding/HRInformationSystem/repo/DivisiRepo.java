package com.juaracoding.HRInformationSystem.repo;

/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author JEJE a.k.a Jefri S
Java Developer
Created On 3/18/2023 00:39
@Last Modified 3/18/2023 00:39
Version 1.0
*/


import com.juaracoding.HRInformationSystem.model.Divisi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisiRepo extends JpaRepository<Divisi,Long> {

    Page<Divisi> findByIsDelete(Pageable page , byte byteIsDelete);

    List<Divisi> findByIsDelete(byte byteIsDelete);
    Page<Divisi> findByIsDeleteAndNamaDivisiContainsIgnoreCase(Pageable page , byte byteIsDelete, String values);
    Page<Divisi> findByIsDeleteAndIdDivisi(Pageable page , byte byteIsDelete, Long values);

}
