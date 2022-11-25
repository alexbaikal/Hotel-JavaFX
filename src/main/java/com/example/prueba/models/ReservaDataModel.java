package com.example.prueba.models;

public class ReservaDataModel {

    private int id_reserva;
    private int id_cliente;
    private int id_recepcionista;
    private int id_habitacion;
    private String fecha_inicio;
    private String fecha_final;
    private String estado;
    private int numero_habitacion;
    private String nombre_cliente;
    private String nombre_recepcionista;
    private String fecha_inicio_string;
    private String fecha_final_string;

    private String estado_reserva;
    private int precio;



    public ReservaDataModel(int id_reserva, int id_cliente, int id_recepcionista, int id_habitacion, String fecha_inicio, String fecha_final, String estado_reserva, String estado) {
        super();
        this.id_reserva = id_reserva;
        this.id_cliente = id_cliente;
        this.id_recepcionista = id_recepcionista;
        this.id_habitacion = id_habitacion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.estado_reserva = estado_reserva;
        this.estado = estado;

        this.numero_habitacion = 0;
        this.nombre_cliente = "";
        this.nombre_recepcionista = "";
        this.fecha_inicio_string = "";
        this.fecha_final_string = "";
        this.precio = 0;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_recepcionista() {
        return id_recepcionista;
    }

    public void setId_recepcionista(int id_recepcionista) {
        this.id_recepcionista = id_recepcionista;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_final() {
        return fecha_final;
    }
    public String getEstado_reserva() {
        return estado_reserva;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }
    public void setEstado_reserva(String estado_reserva) {
        this.estado_reserva = estado_reserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public int getNumero_habitacion() {
        return numero_habitacion;
    }

    public void setNumero_habitacion(int numero_habitacion) {
        this.numero_habitacion = numero_habitacion;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNombre_recepcionista() {
        return nombre_recepcionista;
    }

    public void setNombre_recepcionista(String nombre_recepcionista) {
        this.nombre_recepcionista = nombre_recepcionista;
    }

    public String getFecha_inicio_string() {
        return fecha_inicio_string;
    }

    public void setFecha_inicio_string(String fecha_inicio_string) {
        this.fecha_inicio_string = fecha_inicio_string;
    }



    public String getFecha_final_string() {
        return fecha_final_string;
    }

    public void setFecha_final_string(String fecha_final_string) {
        this.fecha_final_string = fecha_final_string;
    }



    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}


