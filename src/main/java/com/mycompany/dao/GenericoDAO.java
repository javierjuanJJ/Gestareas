/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao;

import java.util.List;

public interface GenericoDAO<Tipo> {

    Tipo findByPK(int id) throws Exception;

    List<Tipo> findAll() throws Exception;

    List<Tipo> findBySQL(String sqlselect) throws Exception;

    boolean insert(Tipo t) throws Exception;

    boolean update(Tipo t) throws Exception;

    boolean delete(int id) throws Exception;
    List<Tipo> findByExample(Tipo example) throws Exception;

}
