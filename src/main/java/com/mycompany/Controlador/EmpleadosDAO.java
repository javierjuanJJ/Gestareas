/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Controlador;

import com.mycompany.Modelo.Empleado;
import com.mycompany.Modelo.Tarea;
import com.mycompany.gestareas_javier_juan_uceda.Controlador_Aplicacion;
import com.mycompany.gestareas_javier_juan_uceda.Controlador_perfil_Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author batoi
 */
public class EmpleadosDAO implements GenericoDAO<Empleado> {
    
    private static EntityManagerFactory emf;
    private static EntityManager conexion;
    
    public EmpleadosDAO() {
        try {
            emf = Conexion.getConnectionemf();
            conexion = Conexion.getConnectionem();
        } catch (Exception ex) {
            
        }
        
    }
    
    public Empleado findByPK(int id) throws Exception {
        TypedQuery<Empleado> query = conexion.createQuery("SELECT c FROM Empleado c WHERE c.id_Empleado= " + id, Empleado.class);
        List<Empleado> Articulos_recibidos = query.getResultList();
        return Articulos_recibidos.get(0);
    }
    
    @Override
    public List<Empleado> findAll() throws Exception {
        TypedQuery<Empleado> query = conexion.createQuery("SELECT c FROM Empleado c", Empleado.class);
        List<Empleado> Articulos_recibidos = query.getResultList();
        return Articulos_recibidos;
    }
    
    @Override
    public List<Empleado> findBySQL(String sqlselect) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean insert(Empleado t) throws Exception {
        conexion.getTransaction().begin();
        conexion.persist(t);
        conexion.getTransaction().commit();
        return true;
    }
    
    @Override
    public boolean update(Empleado t) throws Exception {
        Empleado employee = findByPK(t.getId());
        conexion.getTransaction().begin();
        employee.clone(t);
        conexion.getTransaction().commit();
        Controlador_Aplicacion.empleado = new Empleado(t);
        Controlador_perfil_Usuario.empleado = new Empleado(t);
        return true;
    }
    
    public void eliminar_subtareas (ArrayList<Tarea> lista_de_subtareas){
        for (Tarea subtarea: lista_de_subtareas){
            conexion.getTransaction().begin();
            conexion.remove(subtarea);
            conexion.getTransaction().commit();
            if (subtarea.getLista_subtareas().size() > 0)  eliminar_subtareas(subtarea.getLista_subtareas());
        }
    }
    
    @Override
    public boolean delete(int id) throws Exception {
        Empleado employee = conexion.find(Empleado.class, id);
        conexion.getTransaction().begin();
        conexion.remove(employee);
        conexion.getTransaction().commit();
        eliminar_subtareas(employee.getLista_tareas());
        return true;
    }
    
    public boolean inicio_sesion(String nombre_de_usuario, String contrasenya) throws Exception {
        String contrasenya_encriptado = Empleado.desencriptar_contrasenya(contrasenya);
        boolean inicio_de_sesion = false;
        List<Empleado> lista_empleados = findAll();
        
        for (int contador = 0; contador < lista_empleados.size(); contador++) {
            String contrasenya_a_comparar = Empleado.desencriptar_contrasenya(lista_empleados.get(contador).getContrasenya());
            boolean nombre_igual = (lista_empleados.get(contador).getNombre().equals(nombre_de_usuario));
            boolean contrasenya_igual = (contrasenya_a_comparar.equals(contrasenya_encriptado));
            if ((nombre_igual) && (contrasenya_igual)) {
                inicio_de_sesion = true;
                Controlador_Aplicacion.empleado = new Empleado(lista_empleados.get(contador));
                contador = lista_empleados.size();
            }
        }
        
        return inicio_de_sesion;
        
    }
    
    @Override
    public List<Empleado> findByExample(Object example) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
