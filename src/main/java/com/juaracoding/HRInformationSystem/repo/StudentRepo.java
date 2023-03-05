package com.juaracoding.HRInformationSystem.repo;

import com.juaracoding.HRInformationSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long>{

}
