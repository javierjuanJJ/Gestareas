/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author batoi
 */
public class Tarea {
    
    private int id_tarea;
    private Date fecha_de_alta;
    private Date fecha_de_finalizacion;
    private String descripcion;
    private ArrayList<Tarea> lista_subtareas;
    
    public Tarea(Date fecha_de_alta, Date fecha_de_finalizacion, String descripcion, ArrayList<Tarea> lista_subtareas) {
        this.fecha_de_alta = fecha_de_alta;
        this.fecha_de_finalizacion = fecha_de_finalizacion;
        this.descripcion = descripcion;
        this.lista_subtareas = lista_subtareas;
    }
    
    public Tarea() {
        this.fecha_de_alta = new Date();
        this.fecha_de_finalizacion = new Date();
        this.descripcion = "";
        this.lista_subtareas = new ArrayList();
    }
    
    public Tarea(Tarea tarea) {
        this.fecha_de_alta = tarea.getFecha_de_alta();
        this.fecha_de_finalizacion = tarea.getFecha_de_finalizacion();
        this.descripcion = tarea.getDescripcion();
        this.lista_subtareas = tarea.getLista_subtareas();
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
    
    @Override
    public String toString() {
        return "Tarea " + "descripcion=" + descripcion;
    }
    
}
