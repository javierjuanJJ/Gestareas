/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Modelo;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author batoi
 */
@Entity
public class Empleado {

    @Id
    @GeneratedValue

    private int id_Empleado;
    private String nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String direccion;
    private int telefono;
    private String localidad;  
    private String contrasenya;
    private Date fecha_nacimiento;
    @OneToMany(fetch=FetchType.EAGER)
    private ArrayList<Tarea> lista_tareas;

    public Empleado(String nombre, String primer_apellido, String segundo_apellido, String direccion, int telefono, String localidad, boolean esta_muerto, Date fecha_nacimiento, String contrasenya, ArrayList<Tarea> lista_tareas) {
        this.nombre = nombre;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.localidad = localidad;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lista_tareas = lista_tareas;
        this.contrasenya = contrasenya;
    }

    public Empleado() {
        this.nombre = "";
        this.primer_apellido = "";
        this.segundo_apellido = "";
        this.direccion = "";
        this.telefono = 0;
        this.localidad = "";
        this.fecha_nacimiento = new Date();
        this.lista_tareas = new ArrayList();
    }

    public Empleado(Empleado empleado) {
        this.nombre = empleado.getNombre();
        this.primer_apellido = empleado.getPrimer_apellido();
        this.segundo_apellido = empleado.getSegundo_apellido();
        this.direccion = empleado.getDireccion();
        this.telefono = empleado.getTelefono();
        this.localidad = empleado.getLocalidad();
        this.fecha_nacimiento = empleado.getFecha_nacimiento();
        this.lista_tareas = empleado.getLista_tareas();
        this.id_Empleado = empleado.getId();
        this.contrasenya = empleado.getContrasenya();
    }

    public void clone(Empleado empleado) {
        this.nombre = empleado.getNombre();
        this.primer_apellido = empleado.getPrimer_apellido();
        this.segundo_apellido = empleado.getSegundo_apellido();
        this.direccion = empleado.getDireccion();
        this.telefono = empleado.getTelefono();
        this.localidad = empleado.getLocalidad();
        this.fecha_nacimiento = empleado.getFecha_nacimiento();
        this.lista_tareas = empleado.getLista_tareas();
        this.contrasenya = empleado.getContrasenya();
        this.id_Empleado = empleado.getId();
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public int getId() {
        return id_Empleado;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public ArrayList<Tarea> getLista_tareas() {
        return lista_tareas;
    }

    public void setLista_tareas(ArrayList<Tarea> lista_tareas) {
        this.lista_tareas = lista_tareas;
    }

    @Override
    public String toString() {
        return this.nombre + " " + this.primer_apellido + " " + this.segundo_apellido;
    }

    public static String desencriptar_contrasenya(String texto) {
        String desencripcion = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(texto.getBytes("utf8"));
            desencripcion = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        }
        return desencripcion;

    }

    public void setIdEmpleado(int id_Empleado) {

        this.id_Empleado = id_Empleado;

    }

    public void setContrasenya(String text) {
        this.contrasenya = text;
    }

}
