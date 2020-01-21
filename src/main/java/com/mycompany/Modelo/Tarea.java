/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Modelo;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author batoi
 */
@Entity
public class Tarea implements Comparable<Tarea> {

    @Id
    @GeneratedValue
    private int id_tarea;
    private Date fecha_de_alta;
    private Date fecha_de_finalizacion;
    private String descripcion;
    private boolean esta_realizada;
    private ArrayList<Tarea> lista_subtareas;

    public Tarea(Date fecha_de_alta, Date fecha_de_finalizacion, String descripcion, ArrayList<Tarea> lista_subtareas) {
        this.fecha_de_alta = fecha_de_alta;
        this.fecha_de_finalizacion = fecha_de_finalizacion;
        this.descripcion = descripcion;
        this.lista_subtareas = lista_subtareas;
    }

    public Tarea() {
        this.id_tarea = 0;
        this.fecha_de_alta = new Date();
        this.fecha_de_finalizacion = new Date();
        this.descripcion = "";
        this.lista_subtareas = new ArrayList();
    }

    public Tarea(Tarea tarea) {
        this.id_tarea = tarea.getId_tarea();
        this.fecha_de_alta = tarea.getFecha_de_alta();
        this.fecha_de_finalizacion = tarea.getFecha_de_finalizacion();
        this.descripcion = tarea.getDescripcion();
        this.lista_subtareas = tarea.getLista_subtareas();
        this.esta_realizada = tarea.isEsta_realizada();
    }

    public ArrayList<Tarea> getLista_subtareas() {
        return lista_subtareas;
    }

    public int getId_tarea() {
        return id_tarea;
    }

    public void setLista_subtareas(ArrayList<Tarea> lista_subtareas) {
        this.lista_subtareas = lista_subtareas;
    }

    public boolean isEsta_realizada() {
        return esta_realizada;
    }

    public void setEsta_realizada(boolean esta_realizada) {
        this.esta_realizada = esta_realizada;
    }

    public Date getFecha_de_alta() {
        return fecha_de_alta;
    }

    public void setFecha_de_alta(Date fecha_de_alta) {
        this.fecha_de_alta = fecha_de_alta;
    }

    public Date getFecha_de_finalizacion() {
        return fecha_de_finalizacion;
    }

    public void setFecha_de_finalizacion(Date fecha_de_finalizacion) {
        this.fecha_de_finalizacion = fecha_de_finalizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean equals(Tarea tarea) {

        return this.id_tarea == tarea.getId_tarea() || this.descripcion.equals("");
    }

    @Override
    public String toString() {
        return this.equals(new Tarea()) ? "" : "Tarea " + "descripcion=" + descripcion;
    }

    public void setId(int i) {
        this.id_tarea = i;
    }

    public void clone(Tarea t) {
        this.fecha_de_alta = t.getFecha_de_alta();
        this.fecha_de_finalizacion = t.getFecha_de_finalizacion();
        this.descripcion = t.getDescripcion();
        this.lista_subtareas = t.getLista_subtareas();
        this.esta_realizada = t.isEsta_realizada();

    }

    @Override
    public int compareTo(Tarea t) {
        return getFecha_de_alta().compareTo(t.getFecha_de_alta());

    }

    public boolean esta_entre(Date t) {

        int dia = t.getDate();
        int dia_tarea = getFecha_de_alta().getDate();
        int dia_tarea_finalizacion = getFecha_de_finalizacion().getDate();

        int mes = t.getMonth();
        int mes_tarea = getFecha_de_alta().getMonth();
        int mes_tarea_finalizacion = getFecha_de_finalizacion().getMonth();

        int anyo = t.getYear();
        int anyo_tarea = getFecha_de_alta().getYear();
        int anyo_tarea_finalizacion = getFecha_de_finalizacion().getYear();

        boolean dia_comprobacion = (dia >= dia_tarea) && (dia <= dia_tarea_finalizacion);
        boolean mes_comprobacion = (mes >= mes_tarea) && (mes <= mes_tarea_finalizacion);
        boolean anyo_comprobacion = (anyo >= anyo_tarea) && (anyo <= anyo_tarea_finalizacion);

        return dia_comprobacion && mes_comprobacion && anyo_comprobacion;

    }

}
