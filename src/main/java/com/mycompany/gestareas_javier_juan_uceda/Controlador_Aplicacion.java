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
            for (int contador = 0; contador < this.conexionEmpleados.findAll().size(); contador++) {
                this.conexionEmpleados.findAll().get(contador).setContrasenya("1234");
                System.out.println(this.conexionEmpleados.findAll().get(contador).getNombre() + " " + this.conexionEmpleados.findAll().get(contador).getContrasenya());
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
            String n_u = TextField_nombre_de_usuario_login.getText();
            String pas = TextField_contrasenya_login.getText();
            if (conexionEmpleados.inicio_sesion(n_u, pas)) {

                ocultar_desocultar("todo");
                ArrayList<Tarea> lista_tareas = empleado.getLista_tareas();
                ComboBox_Tareas.getItems().setAll(lista_tareas);
                ComboBox_Empleados_Compartir.getItems().setAll(this.conexionEmpleados.findAll());
                tareas_lista = Llenar_lista_tareas(lista_tareas);
                Llenar_tree();
                coger_informacion_de_empleado(false);
                System.out.println("Sesion iniciada");
            } else {
                System.out.println("Sesion no iniciada");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
            this.conexionEmpleados.delete(empleado.getId());
            empleado = null;
            salir_de_la_sesion();
        } catch (Exception ex) {

        }
    }

    @FXML
    public void Llenar_tareas_y_subtareas() {
        this.ComboBox_Tareas.getItems().setAll(empleado.getLista_tareas());
        this.ComboBox_Empleados_subtareas.getItems().setAll(empleado.getLista_tareas());
    }

    @FXML
    public void Accion_tareas_y_subtareas() {

        this.ComboBox_Tareas.getItems().add(this.ComboBox_SubTareas.getSelectionModel().getSelectedItem());
        this.ComboBox_SubTareas.getItems().setAll(this.ComboBox_SubTareas.getSelectionModel().getSelectedItem().getLista_subtareas());
    }

    @FXML
    public void Llenar_empleados() {
        try {
            this.ComboBox_Empleados_Compartir.getItems().setAll(this.conexionEmpleados.findAll());
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
        Tarea tarea = new Tarea(this.ComboBox_Empleados_subtareas.getSelectionModel().getSelectedItem());

        try {
            tarea.getLista_subtareas().add(tarea_seleccionada);
            conexionTareas.update(tarea);
            Llenar_tree();
        } catch (Exception ex) {

        }

    }

    private TreeItem<Tarea> crear_vista_arbol(ArrayList<Tarea> lista_tareas) {
        TreeItem<Tarea> rootItem = new TreeItem<Tarea>();
        rootItem.setExpanded(true);

        for (Tarea tarea : lista_tareas) {

            if (!tarea.equals(new Tarea())) {
                TreeItem<Tarea> item = new TreeItem<Tarea>();
                item.setValue(tarea);

                System.out.println("Id tarea " + tarea.getId_tarea() + " es igual a por defecto " + tarea.equals(new Tarea()));
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
            if (subtarea) {
                this.conexionTareas.insert(tarea);
                this.conexionEmpleados.update(empleado);
            } else {
                this.conexionTareas.insert(tarea);
                empleado.getLista_tareas().add(tarea);
                this.conexionEmpleados.update(empleado);
            }
            
            ArrayList<Tarea> lista_tareas = empleado.getLista_tareas();
            ComboBox_Tareas.getItems().setAll(lista_tareas);
            tareas_lista = Llenar_lista_tareas(lista_tareas);
            treeview_vista.setRoot(crear_vista_arbol(empleado.getLista_tareas()));

            subtarea = false;

        } catch (Exception ex) {

        }

    }

    private Empleado coger_informacion_de_empleado(boolean coger) {
        Empleado Empleado_creado = null;
        if (coger) {
            empleado = empleado == null ? new Empleado() : empleado;
            Empleado_creado = new Empleado();
            Empleado_creado.setIdEmpleado(empleado.getId());
            Empleado_creado.setDireccion(this.TextField_direccion.getText());
            Empleado_creado.setLocalidad(this.TextField_localidad.getText());
            Empleado_creado.setFecha_nacimiento(Date.from(Instant.from(this.fecha_de_nacimiento_login.getValue().atStartOfDay(ZoneId.systemDefault()))));
            Empleado_creado.setNombre(this.TextField_nombre_de_usuario.getText());
            Empleado_creado.setContrasenya(this.TextField_contrasenya_login1.getText().isEmpty() ? "" : this.TextField_contrasenya_login1.getText());
            Empleado_creado.setPrimer_apellido(this.TextField_primer_apellido.getText());
            Empleado_creado.setSegundo_apellido(this.TextField_segundo_apellido.getText());
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
            this.TextField_direccion.setText(empleado.getDireccion());
            this.TextField_contrasenya_login.setText(empleado.getContrasenya());
            this.TextField_localidad.setText(empleado.getLocalidad());
            this.TextField_nombre_de_usuario.setText(empleado.getNombre());
            this.TextField_primer_apellido.setText(empleado.getPrimer_apellido());
            this.TextField_segundo_apellido.setText(empleado.getSegundo_apellido());
            this.TextField_telefono.setText(String.valueOf(empleado.getTelefono()));
            this.fecha_de_nacimiento_login.setValue(empleado.getFecha_nacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.TextField_contrasenya_login1.setText(empleado.getContrasenya());
        }

        return Empleado_creado;
    }

    private Tarea coger_informacion_de_la_tarea(boolean coger) {
        Tarea Tarea_creada = null;
        if (coger) {
            Tarea_creada = new Tarea();
            Tarea_creada.setDescripcion(this.TextArea_Descripcion.getText());
            Tarea_creada.setEsta_realizada(this.CheckBox_Realizada_la_tarea.isPressed());
            Tarea_creada.setFecha_de_alta(Date.from(Instant.from(this.fecha_de_nacimiento_tarea.getValue().atStartOfDay(ZoneId.systemDefault()))));
            Tarea_creada.setFecha_de_finalizacion(Date.from(Instant.from(this.fecha_de_finalizacion_tarea.getValue().atStartOfDay(ZoneId.systemDefault()))));
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

    }

    @FXML
    public void eliminar_tarea() {

        try {
            tarea_seleccionada.setId(0);
            this.conexionTareas.delete(tarea_seleccionada.getId_tarea());
            tarea_seleccionada = null;
            ArrayList<Tarea> lista_tareas = empleado.getLista_tareas();
            ComboBox_Tareas.getItems().setAll(lista_tareas);
            tareas_lista = Llenar_lista_tareas(lista_tareas);
            treeview_vista.setRoot(crear_vista_arbol(empleado.getLista_tareas()));

        } catch (Exception ex) {

        }

    }

    @FXML
    public void compartir_tarea() {
        Empleado Otro_Empleado = new Empleado(ComboBox_Empleados_Compartir.getSelectionModel().getSelectedItem());
        Tarea tarea = new Tarea(this.ComboBox_Tareas.getSelectionModel().getSelectedItem());

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
            rootItem.add(new Tarea());
            rootItem.add(tarea);
            if (tarea.getLista_subtareas().size() >= 0) {
                rootItem.addAll(Llenar_lista_tareas(tarea.getLista_subtareas()));
            }

        }
        return rootItem;

    }

}
