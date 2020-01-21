/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestareas_javier_juan_uceda;

import com.mycompany.Controlador.EmpleadosDAO;
import com.mycompany.Controlador.TareasDAO;
import com.mycompany.Modelo.Empleado;
import com.mycompany.Modelo.Tarea;
import java.awt.Event;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem.TreeModificationEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author batoi
 */
public class Controlador_Aplicacion {

    public static Empleado empleado;
    public static Tarea tarea_seleccionada;
    private static int contador_tarea = 0;
    private ArrayList<Tarea> tareas_lista;
    private EmpleadosDAO conexionEmpleados;
    private TareasDAO conexionTareas;
    private static boolean subtarea = false;
    @FXML
    private Label label_Nombre_de_Usuario;
    @FXML
    private Label label_Contrasenya;
    @FXML
    private Label label_Tarea;
    @FXML
    private Label label_SubTarea;
    @FXML
    private Label label_Columna_Datos_Nombre_de_Usuario;
    @FXML
    private Label label_Columna_Datos_Primer_Apellido;
    @FXML
    private Label label_Columna_Datos_Segundo_Apellido;
    @FXML
    private Label label_Columna_Datos_Direccion;
    @FXML
    private Label label_Columna_Datos_Telefono;
    @FXML
    private Label label_Columna_Datos_Localidad;
    @FXML
    private Label label_Columna_Fecha_de_nacimiento;
    @FXML
    private Label label_Compartir_a;
    @FXML
    private Label label_anyadir_subtarea_a;
    @FXML
    private Label label_informacion_login;
    @FXML
    private Label label_Contrasenya1;

    @FXML
    private TreeView treeview_vista;

    @FXML
    private Button bt_parte_editar;
    @FXML
    private Button bt_parte_entrar;
    @FXML
    private Button bt_parte_salir;
    @FXML
    private Button bt_parte_agregar;
    @FXML
    private Button bt_insertar_empleado;
    @FXML
    private Button bt_modificar_empleado;
    @FXML
    private Button bt_eliminar_empleado;
    @FXML
    private Button bt_insertar_tarea;
    @FXML
    private Button bt_modificar_tarea;
    @FXML
    private Button bt_eliminar_tarea;
    @FXML
    private Button bt_compartir;
    @FXML
    private Button bt_anyadir_una_subtarea;

    @FXML
    private CheckBox CheckBox_Realizada_la_tarea;

    @FXML
    private DatePicker fecha_de_nacimiento_login;
    @FXML
    private DatePicker fecha_de_finalizacion_tarea;
    @FXML
    private DatePicker fecha_de_nacimiento_tarea;

    @FXML
    private ComboBox<Empleado> ComboBox_Empleados_Compartir;
    @FXML
    private ComboBox<Tarea> ComboBox_Empleados_subtareas;
    @FXML
    private ComboBox<Tarea> ComboBox_Tareas;
    @FXML
    private ComboBox<Tarea> ComboBox_SubTareas;

    @FXML
    private TextField TextField_primer_apellido;
    @FXML
    private TextField TextField_segundo_apellido;
    @FXML
    private TextField TextField_direccion;
    @FXML
    private TextField TextField_telefono;
    @FXML
    private TextField TextField_localidad;
    @FXML
    private PasswordField TextField_contrasenya_login;
    @FXML
    private PasswordField TextField_contrasenya_login1;
    @FXML
    private TextField TextField_nombre_de_usuario_login;
    @FXML
    private TextField TextField_nombre_de_usuario;

    @FXML
    private TextArea TextArea_Descripcion;

    @FXML
    public void initialize() {
        conexionEmpleados = new EmpleadosDAO();
        conexionTareas = new TareasDAO();
        tareas_lista = new ArrayList();
        try {
            for (int contador = 0; contador < conexionEmpleados.findAll().size(); contador++) {
                conexionEmpleados.findAll().get(contador).setContrasenya("1234");
                //System.out.println(conexionEmpleados.findAll().get(contador).getNombre() + " " + conexionEmpleados.findAll().get(contador).getContrasenya());
            }
        } catch (Exception ex) {

        }

        ocultar_desocultar("login");

    }

    @FXML
    public void poner_columnas() {
        ocultar_desocultar("informacion_usuario");
    }

    @FXML
    public void iniciar_sesion() {
        try {
            if (conexionEmpleados.inicio_sesion(TextField_nombre_de_usuario_login.getText(), TextField_contrasenya_login.getText())) {
                Collections.sort((empleado.getLista_tareas()));
                ocultar_desocultar("todo");
                ArrayList<Tarea> lista_tareas = empleado.getLista_tareas();
                ComboBox_Tareas.getItems().setAll(lista_tareas);
                ComboBox_Empleados_Compartir.getItems().setAll(conexionEmpleados.findAll());
                tareas_lista = Llenar_lista_tareas(lista_tareas);
                Llenar_tree();
                coger_informacion_de_empleado(false);

            } else {

            }
        } catch (Exception ex) {
            
        }
    }
    
    @FXML
    public void insertar_empleado() {
        try {
            conexionEmpleados.insert(coger_informacion_de_empleado(true));
        } catch (Exception ex) {

        }
    }

    @FXML
    public void modificar_empleado() {

        try {
            conexionEmpleados.update(coger_informacion_de_empleado(true));
        } catch (Exception ex) {

        }

    }

    @FXML
    public void salir_de_la_sesion() {
        try {
            App.Cambiar_Pantalla();
        } catch (IOException ex) {

        }
    }

    @FXML
    public void eliminar_empleado() {
        try {
            conexionEmpleados.delete(empleado.getId());
            empleado = null;
            salir_de_la_sesion();
        } catch (Exception ex) {

        }
    }

    @FXML
    public void Llenar_tareas_y_subtareas() {
        ComboBox_Tareas.getItems().setAll(empleado.getLista_tareas());
        ComboBox_Empleados_subtareas.getItems().setAll(empleado.getLista_tareas());
    }

    @FXML
    public void Accion_tareas_y_subtareas() {

        ComboBox_Tareas.getItems().add(ComboBox_SubTareas.getSelectionModel().getSelectedItem());
        ComboBox_SubTareas.getItems().setAll(ComboBox_SubTareas.getSelectionModel().getSelectedItem().getLista_subtareas());
    }

    @FXML
    public void Llenar_empleados() {
        try {
            ComboBox_Empleados_Compartir.getItems().setAll(conexionEmpleados.findAll());
        } catch (Exception ex) {

        }
    }

    @FXML
    public void Poner_tarea_tree() {

        tarea_seleccionada = new Tarea(tareas_lista.get(treeview_vista.getSelectionModel().getSelectedIndex()));
        coger_informacion_de_la_tarea(false);

    }

    public void Llenar_tree() {
        treeview_vista.setRoot(crear_vista_arbol(empleado.getLista_tareas()));
    }

    @FXML
    public void Anyadir_una_subtarea() {
        subtarea = true;
        insertar_tarea();
        Tarea tarea = new Tarea(ComboBox_Empleados_subtareas.getSelectionModel().getSelectedItem());

        try {
            tarea.getLista_subtareas().add(tarea_seleccionada);           
            conexionTareas.update(tarea);
            Collections.sort((empleado.getLista_tareas()));
            Llenar_tree();
        } catch (Exception ex) {

        }
        
        

    }

    private TreeItem<Tarea> crear_vista_arbol(ArrayList<Tarea> lista_tareas) {
        TreeItem<Tarea> rootItem = new TreeItem();
        rootItem.setExpanded(true);

        for (Tarea tarea : lista_tareas) {

            if (!tarea.equals(new Tarea())) {
                TreeItem<Tarea> item = new TreeItem();
                item.setValue(tarea);
                rootItem.getChildren().add(item);
                if (tarea.getLista_subtareas().size() >= 0) {
                    rootItem.getChildren().add(crear_vista_arbol(tarea.getLista_subtareas()));
                }
            }
        }
        return rootItem;
    }

    @FXML
    public void insertar_tarea() {

        try {
            Tarea tarea = coger_informacion_de_la_tarea(true);
            conexionTareas.insert(tarea);
            
            if (!subtarea) {
                empleado.getLista_tareas().add(tarea_seleccionada);
            }
            
            //System.out.println(tarea_seleccionada.getDescripcion() + " " + tarea_seleccionada.getId_tarea());
            
            conexionEmpleados.update(empleado);
            Collections.sort((empleado.getLista_tareas()));
            ArrayList<Tarea> lista_tareas = empleado.getLista_tareas();
            ComboBox_Tareas.getItems().setAll(lista_tareas);
            tareas_lista = Llenar_lista_tareas(lista_tareas);
            this.Llenar_tree();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private Empleado coger_informacion_de_empleado(boolean coger) {
        Empleado Empleado_creado = null;
        if (coger) {
            empleado = empleado == null ? new Empleado() : empleado;
            Empleado_creado = new Empleado();
            Empleado_creado.setIdEmpleado(empleado.getId());
            Empleado_creado.setDireccion(TextField_direccion.getText());
            Empleado_creado.setLocalidad(TextField_localidad.getText());
            Empleado_creado.setFecha_nacimiento(Date.from(Instant.from(fecha_de_nacimiento_login.getValue().atStartOfDay(ZoneId.systemDefault()))));
            Empleado_creado.setNombre(TextField_nombre_de_usuario.getText());
            Empleado_creado.setContrasenya(TextField_contrasenya_login1.getText().isEmpty() ? "" : TextField_contrasenya_login1.getText());
            Empleado_creado.setPrimer_apellido(TextField_primer_apellido.getText());
            Empleado_creado.setSegundo_apellido(TextField_segundo_apellido.getText());
            Empleado_creado.setLista_tareas(empleado.getLista_tareas());

            int telefono = 0;

            try {
                telefono = Integer.parseInt(TextField_telefono.getText());
            } catch (NumberFormatException e) {
                telefono = 0;
            }

            Empleado_creado.setTelefono(telefono);
        } else {
            Empleado_creado = new Empleado(empleado);
            TextField_direccion.setText(empleado.getDireccion());
            TextField_contrasenya_login.setText(empleado.getContrasenya());
            TextField_localidad.setText(empleado.getLocalidad());
            TextField_nombre_de_usuario.setText(empleado.getNombre());
            TextField_primer_apellido.setText(empleado.getPrimer_apellido());
            TextField_segundo_apellido.setText(empleado.getSegundo_apellido());
            TextField_telefono.setText(String.valueOf(empleado.getTelefono()));
            fecha_de_nacimiento_login.setValue(empleado.getFecha_nacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            TextField_contrasenya_login1.setText(empleado.getContrasenya());
        }

        return Empleado_creado;
    }

    private Tarea coger_informacion_de_la_tarea(boolean coger) {
        Tarea Tarea_creada = null;
        if (coger) {
            Tarea_creada = new Tarea();
            if (tarea_seleccionada != null) {
                Tarea_creada.setId(tarea_seleccionada.getId_tarea());
                Tarea_creada.setLista_subtareas(tarea_seleccionada.getLista_subtareas());
            }
                Tarea_creada.setDescripcion(TextArea_Descripcion.getText());
                Tarea_creada.setEsta_realizada(CheckBox_Realizada_la_tarea.isPressed());
                Tarea_creada.setFecha_de_alta(Date.from(Instant.from(fecha_de_nacimiento_tarea.getValue().atStartOfDay(ZoneId.systemDefault()))));
                Tarea_creada.setFecha_de_finalizacion(Date.from(Instant.from(fecha_de_finalizacion_tarea.getValue().atStartOfDay(ZoneId.systemDefault()))));
        } else {
            Tarea_creada = new Tarea(tarea_seleccionada);

            if (!Tarea_creada.equals(new Tarea())) {
                fecha_de_nacimiento_tarea.setValue(tarea_seleccionada.getFecha_de_alta().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                fecha_de_finalizacion_tarea.setValue(tarea_seleccionada.getFecha_de_finalizacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                TextArea_Descripcion.setText(tarea_seleccionada.getDescripcion());
                CheckBox_Realizada_la_tarea.setSelected(tarea_seleccionada.isEsta_realizada());
            }
        }

        return Tarea_creada;
    }

    @FXML
    public void modificar_tarea() {

        try {
            conexionTareas.update(coger_informacion_de_la_tarea(true));
Collections.sort((empleado.getLista_tareas()));
            ArrayList<Tarea> lista_tareas = empleado.getLista_tareas();
            
            for (Tarea tarea: lista_tareas){
                if (tarea.equals(tarea_seleccionada)) {
                    tarea.clone(tarea_seleccionada);
                }
            }
            
            ComboBox_Tareas.getItems().setAll(lista_tareas);
            ComboBox_Empleados_Compartir.getItems().setAll(conexionEmpleados.findAll());
            tareas_lista = Llenar_lista_tareas(lista_tareas);
            Llenar_tree();
        } catch (Exception ex) {

        }

    }

    @FXML
    public void eliminar_tarea() {

        try {
            empleado.getLista_tareas().remove(tarea_seleccionada);
            conexionTareas.delete(tarea_seleccionada.getId_tarea());
            //tarea_seleccionada.setId(0);                
            ArrayList<Tarea> lista_tareas = empleado.getLista_tareas();
            lista_tareas.remove(tarea_seleccionada);
            
            ComboBox_Tareas.getItems().setAll(lista_tareas);
            tareas_lista = Llenar_lista_tareas(lista_tareas);
            Llenar_tree();

        } catch (Exception ex) {
            
        }

    }

    @FXML
    public void compartir_tarea() {
        Empleado Otro_Empleado = new Empleado(ComboBox_Empleados_Compartir.getSelectionModel().getSelectedItem());
        Tarea tarea = new Tarea(ComboBox_Tareas.getSelectionModel().getSelectedItem());

        Otro_Empleado.getLista_tareas().add(tarea);
        try {
            conexionEmpleados.update(Otro_Empleado);
        } catch (Exception ex) {

        }
    }

    public void ocultar_desocultar(String accion) {

        label_Nombre_de_Usuario.setVisible(false);
        label_Contrasenya.setVisible(false);
        label_Tarea.setVisible(false);
        label_SubTarea.setVisible(false);
        label_Columna_Datos_Nombre_de_Usuario.setVisible(false);
        label_Columna_Datos_Primer_Apellido.setVisible(false);
        label_Columna_Datos_Segundo_Apellido.setVisible(false);
        label_Columna_Datos_Direccion.setVisible(false);
        label_Columna_Datos_Telefono.setVisible(false);
        label_Columna_Datos_Localidad.setVisible(false);
        label_Columna_Fecha_de_nacimiento.setVisible(false);
        label_Compartir_a.setVisible(false);
        label_anyadir_subtarea_a.setVisible(false);
        label_informacion_login.setVisible(false);
        treeview_vista.setVisible(false);
        bt_parte_editar.setVisible(false);
        bt_parte_entrar.setVisible(false);
        bt_parte_salir.setVisible(false);
        bt_parte_agregar.setVisible(false);
        bt_insertar_empleado.setVisible(false);
        bt_modificar_empleado.setVisible(false);
        bt_eliminar_empleado.setVisible(false);
        bt_insertar_tarea.setVisible(false);
        bt_modificar_tarea.setVisible(false);
        bt_eliminar_tarea.setVisible(false);
        bt_compartir.setVisible(false);
        bt_anyadir_una_subtarea.setVisible(false);
        CheckBox_Realizada_la_tarea.setVisible(false);
        fecha_de_nacimiento_login.setVisible(false);
        fecha_de_finalizacion_tarea.setVisible(false);
        fecha_de_nacimiento_tarea.setVisible(false);
        ComboBox_Empleados_Compartir.setVisible(false);
        ComboBox_Empleados_subtareas.setVisible(false);
        ComboBox_Tareas.setVisible(false);
        ComboBox_SubTareas.setVisible(false);
        TextField_primer_apellido.setVisible(false);
        TextField_segundo_apellido.setVisible(false);
        TextField_direccion.setVisible(false);
        TextField_telefono.setVisible(false);
        TextField_localidad.setVisible(false);
        TextField_nombre_de_usuario.setVisible(false);

        switch (accion) {
            case "informacion_usuario":
                TextField_nombre_de_usuario.setVisible(true);
                bt_insertar_empleado.setVisible(true);
                bt_modificar_empleado.setVisible(true);
                bt_eliminar_empleado.setVisible(true);
                label_Columna_Datos_Nombre_de_Usuario.setVisible(true);
                label_Columna_Datos_Primer_Apellido.setVisible(true);
                label_Columna_Datos_Segundo_Apellido.setVisible(true);
                label_Columna_Datos_Direccion.setVisible(true);
                label_Columna_Datos_Telefono.setVisible(true);
                label_Columna_Datos_Localidad.setVisible(true);
                label_Columna_Fecha_de_nacimiento.setVisible(true);
                TextField_primer_apellido.setVisible(true);
                TextField_segundo_apellido.setVisible(true);
                TextField_direccion.setVisible(true);
                TextField_telefono.setVisible(true);
                TextField_localidad.setVisible(true);
                label_Nombre_de_Usuario.setVisible(true);
                label_Contrasenya.setVisible(true);
                TextField_nombre_de_usuario_login.setVisible(true);
                TextField_contrasenya_login.setVisible(true);
                fecha_de_nacimiento_login.setVisible(true);
                bt_parte_editar.setVisible(true);
                bt_parte_entrar.setVisible(true);
                bt_parte_salir.setVisible(true);
                bt_parte_agregar.setVisible(true);
                TextField_contrasenya_login1.setVisible(true);
                label_Contrasenya1.setVisible(true);

                break;

            case "login":

                label_Nombre_de_Usuario.setVisible(true);
                label_Contrasenya.setVisible(true);
                TextField_nombre_de_usuario_login.setVisible(true);
                TextField_contrasenya_login.setVisible(true);
                bt_parte_editar.setVisible(true);
                bt_parte_entrar.setVisible(true);
                bt_parte_salir.setVisible(true);
                bt_parte_agregar.setVisible(true);

                break;

            case "todo":

                label_Nombre_de_Usuario.setVisible(true);
                label_Contrasenya.setVisible(true);
                label_Tarea.setVisible(true);
                label_SubTarea.setVisible(true);
                label_Columna_Datos_Nombre_de_Usuario.setVisible(true);
                label_Columna_Datos_Primer_Apellido.setVisible(true);
                label_Columna_Datos_Segundo_Apellido.setVisible(true);
                label_Columna_Datos_Direccion.setVisible(true);
                label_Columna_Datos_Telefono.setVisible(true);
                label_Columna_Datos_Localidad.setVisible(true);
                label_Columna_Fecha_de_nacimiento.setVisible(true);
                label_Compartir_a.setVisible(true);
                label_anyadir_subtarea_a.setVisible(true);
                label_informacion_login.setVisible(true);
                treeview_vista.setVisible(true);
                bt_parte_editar.setVisible(true);
                bt_parte_entrar.setVisible(true);
                bt_parte_salir.setVisible(true);
                bt_parte_agregar.setVisible(true);
                bt_insertar_empleado.setVisible(true);
                bt_modificar_empleado.setVisible(true);
                bt_eliminar_empleado.setVisible(true);
                bt_insertar_tarea.setVisible(true);
                bt_modificar_tarea.setVisible(true);
                bt_eliminar_tarea.setVisible(true);
                bt_compartir.setVisible(true);
                bt_anyadir_una_subtarea.setVisible(true);
                CheckBox_Realizada_la_tarea.setVisible(true);
                fecha_de_nacimiento_login.setVisible(true);
                fecha_de_finalizacion_tarea.setVisible(true);
                fecha_de_nacimiento_tarea.setVisible(true);
                ComboBox_Empleados_Compartir.setVisible(true);
                ComboBox_Empleados_subtareas.setVisible(true);
                ComboBox_Tareas.setVisible(true);
                ComboBox_SubTareas.setVisible(true);
                TextField_primer_apellido.setVisible(true);
                TextField_segundo_apellido.setVisible(true);
                TextField_direccion.setVisible(true);
                TextField_telefono.setVisible(true);
                TextField_localidad.setVisible(true);
                TextField_nombre_de_usuario.setVisible(true);

                break;
            default:
                break;
        }

    }

    private ArrayList<Tarea> Llenar_lista_tareas(ArrayList<Tarea> lista_tareas) {
        ArrayList<Tarea> rootItem = new ArrayList();

        for (Tarea tarea : lista_tareas) {

            if (!tarea.equals(new Tarea())) {  
                rootItem.add(new Tarea());
                rootItem.add(tarea);
                if (tarea.getLista_subtareas().size() >= 0) {
                    rootItem.addAll(Llenar_lista_tareas(tarea.getLista_subtareas()));
                }
            }
        }
        return rootItem;

    }

}
