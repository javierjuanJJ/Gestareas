package com.mycompany.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class Conexion {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static final String url = "objectdb:tareas.odb;drop";

    public static EntityManagerFactory getConnectionemf() throws PersistenceException,Exception {

       
        return emf;

    }

    public static EntityManager getConnectionem() throws Exception {

        if (em == null) {
            em = Persistence.createEntityManagerFactory(url).createEntityManager();
        }

        return em;

    }

}
