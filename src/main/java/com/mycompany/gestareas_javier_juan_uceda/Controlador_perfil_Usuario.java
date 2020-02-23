/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestareas_javier_juan_uceda;

import com.mycompany.dao.EmpleadosDAO;
import com.mycompany.dao.TareasDAO;
import com.mycompany.Modelo.Empleado;
import com.mycompany.Modelo.Tarea;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author batoi
 */
public class Controlador_perfil_Usuario {

    public static Empleado empleado;
    public static Tarea tarea_seleccionada;
    private ArrayList<Tarea> tareas_lista;
    private EmpleadosDAO conexionEmpleados;
    private TareasDAO conexionTareas;
    private static boolean subtarea = false;
    private static boolean nuevo_usuario = false;
    @FXML
    private Label label_Nombre_de_Usuario;
    @FXML
    private Label label_Contrasenya;
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
    private Label label_crear_fecha_inicio;
    @FXML
    private Label label_crear_fecha_finalizacion;
    @FXML
    private Label label_crear_descripcion;
    @FXML
    private ComboBox<Empleado> ComboBox_Empleados_Compartir;
    @FXML
    private ComboBox<Tarea> ComboBox_Empleados_subtareas;
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
    private AnchorPane escritorio;

    @FXML
    public void initialize() {
        try {
            conexionEmpleados = new EmpleadosDAO();
            conexionTareas = new TareasDAO();
            tareas_lista = new ArrayList();
            empleado = Controlador_Aplicacion.empleado != null ? Controlador_Aplicacion.empleado : new Empleado();
           
            coger_informacion_de_empleado(false);

        } catch (Exception ex) {

        }
    }

    private Empleado coger_informacion_de_empleado(boolean coger) {
        Empleado Empleado_creado = null;

        try {
            if (coger) {
                empleado = empleado == null ? new Empleado() : empleado;
                Empleado_creado = new Empleado();
                Empleado_creado.setIdEmpleado(empleado.getId());
                Empleado_creado.setDireccion(TextField_direccion.getText());
                Empleado_creado.setLocalidad(TextField_localidad.getText());
                Empleado_creado.setFecha_nacimiento(Date.from(Instant.from(fecha_de_nacimiento_login.getValue().atStartOfDay(ZoneId.systemDefault()))));
                Empleado_creado.setNombre(TextField_nombre_de_usuario.getText());
                Empleado_creado.setContrasenya(TextField_contrasenya_login1.getText() == null || TextField_contrasenya_login1.getText().isEmpty() ? empleado.getContrasenya() : TextField_contrasenya_login1.getText());
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
                TextField_localidad.setText(empleado.getLocalidad());
                TextField_nombre_de_usuario.setText(empleado.getNombre());
                TextField_primer_apellido.setText(empleado.getPrimer_apellido());
                TextField_segundo_apellido.setText(empleado.getSegundo_apellido());
                TextField_telefono.setText(String.valueOf(empleado.getTelefono()));
                fecha_de_nacimiento_login.setValue(empleado.getFecha_nacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                TextField_contrasenya_login1.setText(empleado.getContrasenya());
            }
        } catch (Exception ex) {

        }
        return Empleado_creado;
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
    public void eliminar_empleado() {
        try {
            conexionEmpleados.delete(empleado.getId());
            salir_de_la_sesion();
        } catch (Exception ex) {

        }
    }

    @FXML
    public void salir_de_la_sesion() {
        try {
            empleado = null;
            App.Cambiar_Pantalla("tareas.fxml");
        } catch (IOException ex) {

        }
    }

}
