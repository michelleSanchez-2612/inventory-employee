package com.inventory.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "vaccine")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //autoincrement field
    private Long cod_vaccine;

    @Column(name = "vaccine_type", nullable = false)
    @NotBlank(message = "Tipo de vacuna es obligatorio")
    @Size(max=15, message = "Tamaño de campo excedido")
    @Pattern(regexp="^(Sputnik|AstraZeneca|Pfizer|Jhonson&Jhonson)",message = "Tipo de Vacuna no válida")
    private String vaccineType;


    @Column(name = "vaccine_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vaccinationDate;


    @Column(name = "vaccine_dose", nullable = false)
    @Digits(integer= 2, fraction = 0, message = "Solo se permiten números")
    private int vaccinationDose;


    @ManyToOne
    @JoinColumn(name = "identification", nullable = false)
    @JsonIgnore
    private Employee employee = new Employee();


    public Vaccine() {
    }

    public Vaccine(String vaccineType, Date vaccinationDate, int vaccinationDose) {
        this.vaccineType = vaccineType;
        this.vaccinationDate = vaccinationDate;
        this.vaccinationDose = vaccinationDose;
    }

    public Long getCod_vaccine() {
        return cod_vaccine;
    }

    public void setCod_vaccine(Long cod_vaccine) {
        this.cod_vaccine = cod_vaccine;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public Date getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(Date vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public int getVaccinationDose() {
        return vaccinationDose;
    }

    public void setVaccinationDose(int vaccinationDose) {
        this.vaccinationDose = vaccinationDose;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
