package com.juaracoding.HRInformationSystem.repo;

import com.juaracoding.HRInformationSystem.model.Userz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepo extends JpaRepository<Userz,Long> {

    public List<Userz> findByEmail(String value);
    public List<Userz> findByEmailOrNoHPOrUsername(String emails, String noHP, String userName);

}