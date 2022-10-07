package com.example.prueba.models;

public class ReceptionModel {

    private int id;
    private String name;
    private String surname;
    private String dni;
    private String nationality;
    private String phone;
    private String email;

    private String username;

    private String password;
    private int active_recepcionista;




    public ReceptionModel(int id, String name, String surname, String dni, String nationality, String phone, String email, String username, String password, int active_recepcionista) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.active_recepcionista = active_recepcionista;
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

    public int getActive_recepcionista() {
        return active_recepcionista;
    }

    public void setActive_recepcionista(int active_recepcionista) {
        this.active_recepcionista = active_recepcionista;
    }
}


