package com.inventory.employee.repository;

import com.inventory.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByIdentificationEquals(String identification);

    @Transactional
    long deleteByIdentificationEquals(String identification);

    List<Employee> findByVaccinationStateEquals(String vaccinationState);

    List<Employee> findByVaccineInformation_VaccineTypeEqualsIgnoreCase(String vaccineType);

    List<Employee> findByVaccineInformation_VaccinationDateBetween(Date vaccinationDateStart, Date vaccinationDateEnd);

}