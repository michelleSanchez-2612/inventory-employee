package com.inventory.employee.controller;

import com.inventory.employee.exception.ResourceNotFoundException;
import com.inventory.employee.model.Employee;
import com.inventory.employee.model.Vaccine;
import com.inventory.employee.repository.EmployeeRepository;
import com.inventory.employee.repository.VaccineRepository;
import com.inventory.employee.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class EmployeeCotroller {

    @Autowired //injecting dependency
    private EmployeeRepository employeeRepository ;
    @Autowired //injecting dependency
    private VaccineRepository vaccineRepository ;

    //ADMIN FUNCTIONS

    //Create employee
    @PostMapping("/admin/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee) {
        try {
            Utils utils = new Utils();
            //create new userName and password
            String userName = employee.getNames().split(" ")[0].toLowerCase() + employee.getIdentification().substring(7, 10);
            String password = utils.generateRandomAlphaNumeric(10);

            Employee newEmployee = employeeRepository.save(
                    new Employee(
                            employee.getIdentification(),
                            employee.getNames(),
                            employee.getLastNames(),
                            employee.getEmail(),
                            userName,
                            password
                    )
            );
            return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Update employee
    @PutMapping("/admin/employees/{identification}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("identification") String identification,
                                                   @RequestBody @Valid Employee employee) {
        Optional<Employee> employeeData = employeeRepository.findByIdentificationEquals(identification);

        if (employeeData.isPresent()) {
            Employee updEmployee = employeeData.get();
            updEmployee.setNames(employee.getNames());
            updEmployee.setLastNames(employee.getLastNames());
            updEmployee.setEmail(employee.getEmail());
            return new ResponseEntity<>(employeeRepository.save(updEmployee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete employee
    @DeleteMapping("/admin/employees/{identification}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("identification") String identification) {
        try {
            Long response = employeeRepository.deleteByIdentificationEquals(identification);
            if(response==1){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all employees
    @GetMapping("/admin/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try{
            List<Employee> employees = employeeRepository.findAll();

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Get employee by state vaccination
    @GetMapping("/admin/employees/filter/stateVaccination/{stateVaccination}")
    public ResponseEntity<List<Employee>> getEmployeeByStateVaccination(@PathVariable("stateVaccination") String stateVaccination) {
        try{
            List<Employee> employees = employeeRepository.findByVaccinationStateEquals(stateVaccination);

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get employee by vaccine type
    @GetMapping("/admin/employees/filter/vaccinationType/{vaccineType}")
    public ResponseEntity<List<Employee>> geEmployeeByVaccineType(@PathVariable("vaccineType") String vaccineType) {
        try{
            List<Employee> employees = employeeRepository.findByVaccineInformation_VaccineTypeEqualsIgnoreCase(vaccineType);

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get employee by vaccination date
    @GetMapping("/admin/employees/filter/vaccinationDate/{startDate}/{finishDate}")
    public ResponseEntity<List<Employee>> geEmployeeByVaccinationDates(@PathVariable("startDate") String startDate,
                                                                       @PathVariable("finishDate") String finishDate) {
        try{
            Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date fDate = new SimpleDateFormat("yyyy-MM-dd").parse(finishDate);
            List<Employee> employees = employeeRepository.findByVaccineInformation_VaccinationDateBetween(sDate, fDate);

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //EMPLOYEES FUNCTIONS
    @GetMapping("/public/employees/{identification}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("identification") String identification) {
        Optional<Employee> employeeData = employeeRepository.findByIdentificationEquals(identification);

        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update employee
    @PutMapping("/public/employees/{identification}")
    public ResponseEntity<Employee> updateEmployeePublicInfo(@PathVariable("identification") String identification,
                                                             @RequestBody @Valid Employee employee) {
        Optional<Employee> employeeData = employeeRepository.findByIdentificationEquals(identification);

        if (employeeData.isPresent()) {
            Employee updEmployee = employeeData.get();
            updEmployee.setBirthdate(employee.getBirthdate());
            updEmployee.setDirection(employee.getDirection());
            updEmployee.setPhone(employee.getPhone());
            updEmployee.setVaccinationState(employee.getVaccinationState());

            return new ResponseEntity<>(employeeRepository.save(updEmployee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Create vaccine info
    @PostMapping("/public/employees/{identification}/vaccineInformation")
    public ResponseEntity<Vaccine> createVaccineInformation(@PathVariable("identification") String identification,
                                                            @RequestBody @Valid Vaccine vaccine) {
        try {
            Optional<Employee> employeeData = employeeRepository.findByIdentificationEquals(identification);

            if(employeeData.isPresent()){
                if(employeeData.get().getVaccinationState().equals("Vacunado")){
                    Vaccine newVaccine = new Vaccine(
                            vaccine.getVaccineType(),
                            vaccine.getVaccinationDate(),
                            vaccine.getVaccinationDose()
                    );
                    newVaccine.setEmployee(employeeData.get());
                    vaccineRepository.save(newVaccine);
                    return new ResponseEntity<>(newVaccine, HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>(null, HttpStatus.valueOf("Empleado no ha sido vacunado"));
                }
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Create vaccine info
    @PutMapping("/public/employees/vaccineInformation/{cod_vaccine}")
    public ResponseEntity<Vaccine> updateVaccineInformation(@PathVariable("cod_vaccine") Long codVaccine,
                                                            @RequestBody @Valid Vaccine vaccine) {
        try {
            Optional<Vaccine> vaccineData = vaccineRepository.findById(codVaccine);

            if(vaccineData.isPresent()){
                Vaccine updVaccine = vaccineData.get();
                updVaccine.setVaccineType(vaccine.getVaccineType());
                updVaccine.setVaccinationDate(vaccine.getVaccinationDate());
                updVaccine.setVaccinationDose(vaccine.getVaccinationDose());
                return new ResponseEntity<>(vaccineRepository.save(updVaccine), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
