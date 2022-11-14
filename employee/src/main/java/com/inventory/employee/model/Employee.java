package com.inventory.employee.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "identification", nullable = false)
    @NotBlank(message = "Número de Cédula es obligatorio")
    @Size(min=10, max=10, message = "Número de Cédula debe tener 10 dígitos")
    @Digits(integer= 10, fraction = 0, message = "Solo se permiten números")
    private String identification;

    @Column(name = "names", nullable = false)
    @NotBlank(message = "Nombres es obligatorio")
    @Size(max=100, message = "Tamaño de campo excedido")
    @Pattern(regexp="^[ A-Za-zñÑáéíóúÁÉÍÓÚ]*$",message = "Solo se permiten letras")
    private String names;

    @Column(name = "lastNames", nullable = false)
    @NotBlank(message = "Apellidos es obligatorio")
    @Size(max=100, message = "Tamaño de campo excedido")
    @Pattern(regexp="^[ A-Za-zñÑáéíóúÁÉÍÓÚ]*$",message = "Solo se permiten letras")
    private String lastNames;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Correo Electrónico es obligatorio")
    @Size(max=100, message = "Tamaño de campo excedido")
    @Email
    private String email;

    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date birthdate;

    @Column(name = "direction")
    @Size(max=250, message = "Tamaño de campo excedido")
    private String direction;

    @Column(name = "phone")
    @Size(max=32, message = "Tamaño de campo excedido")
    private String phone;

    @Column(name = "userName", nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "state_vaccination")
    @Pattern(regexp="^(Vacunado|No Vacunado)",message = "Estado de vacunación no válido")
    private String vaccinationState;


    //vaccine information
    @OneToMany(mappedBy = "employee")
    private List<Vaccine> vaccineInformation = new ArrayList<>();

    public Employee() {
    }

    public Employee(String identification, String names, String lastNames, String email, String userName, String password) {
        this.identification = identification;
        this.names = names;
        this.lastNames = lastNames;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public Employee(Date birthdate, String direction, String phone, String vaccinationState) {
        this.birthdate = birthdate;
        this.direction = direction;
        this.phone = phone;
        this.vaccinationState = vaccinationState;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVaccinationState() {
        return vaccinationState;
    }

    public void setVaccinationState(String vaccinationState) {
        this.vaccinationState = vaccinationState;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Vaccine> getVaccineInformation() {
        return vaccineInformation;
    }

    public void setVaccineInformation(List<Vaccine> vaccineInformation) {
        this.vaccineInformation = vaccineInformation;
    }
}
