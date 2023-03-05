package com.juaracoding.HRInformationSystem.repo;


import com.juaracoding.HRInformationSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}