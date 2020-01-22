package com.mycompany.Controlador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class Conexion {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static final String url = "objectdb:tareas.odb;drop";

    public static EntityManagerFactory getConnectionemf() throws Exception {

        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory(url);
            } catch (PersistenceException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        return emf;

    }

    public static EntityManager getConnectionem() throws Exception {

        if (em == null) {
            try {
                em = emf.createEntityManager();
            } catch (PersistenceException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        return em;

    }

}
