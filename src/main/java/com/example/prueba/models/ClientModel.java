package com.example.prueba.models;

public class ClientModel {

    private int id;
    private String name;
    private String surname;
    private String dni;
    private String nationality;
    private String phone;
    private String email;
    private String occupation;
    private String civilState;



    public ClientModel(int id, String name, String surname, String dni, String nationality, String phone, String email, String occupation, String civilState) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
        this.occupation = occupation;
        this.civilState =  civilState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCivilState() {
        return civilState;
    }

    public void setCivilState(String civilState) {
        this.civilState = civilState;
    }
}


