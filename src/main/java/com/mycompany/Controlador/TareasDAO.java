package com.mycompany.Controlador;

import com.mycompany.Modelo.Tarea;
import com.mycompany.gestareas_javier_juan_uceda.Controlador_Aplicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author batoi
 */
public class TareasDAO implements GenericoDAO<Tarea> {

    private static EntityManagerFactory emf;
    private static EntityManager conexion;

    public TareasDAO() {
        try {
            emf = Conexion.getConnectionemf();
            conexion = Conexion.getConnectionem();
        } catch (Exception ex) {

        }

    }

    @Override
    public Tarea findByPK(int id) throws Exception {
        TypedQuery<Tarea> query = conexion.createQuery("SELECT c FROM Tarea c WHERE c.id_tarea= " + id, Tarea.class);
        List<Tarea> Articulos_recibidos = query.getResultList();
        return Articulos_recibidos.get(0);

    }

    @Override
    public List<Tarea> findAll() throws Exception {
        TypedQuery<Tarea> query = conexion.createQuery("SELECT c FROM Tarea c", Tarea.class);
        List<Tarea> Articulos_recibidos = query.getResultList();
        return Articulos_recibidos;

    }

    @Override
    public List<Tarea> findBySQL(String sqlselect) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Tarea t) throws Exception {

        conexion.getTransaction().begin();
        conexion.persist(t);
        conexion.getTransaction().commit();
        Controlador_Aplicacion.tarea_seleccionada = new Tarea(t);
        return true;
    }

    @Override
    public boolean update(Tarea t) throws Exception {
        Tarea employee = findByPK(t.getId_tarea());
        conexion.getTransaction().begin();
        employee.clone(t);
        conexion.getTransaction().commit();
        Controlador_Aplicacion.tarea_seleccionada = new Tarea(employee);
        return true;
    }

    private void eliminar_subtareas(ArrayList<Tarea> lista_subtareas) {
        for (Tarea tarea : lista_subtareas) {
            conexion.getTransaction().begin();
            conexion.remove(tarea);
            conexion.getTransaction().commit();
            try {
                if (tarea.getLista_subtareas().size() > 0) {
                    eliminar_subtareas(tarea.getLista_subtareas());
                }
            } catch (StackOverflowError e) {

            }

        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        Tarea employee = conexion.find(Tarea.class, id);
        conexion.getTransaction().begin();
        conexion.remove(employee);
        conexion.getTransaction().commit();
        eliminar_subtareas(employee.getLista_subtareas());       
        return true;

    }

    @Override
    public List<Tarea> findByExample(Object example) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
