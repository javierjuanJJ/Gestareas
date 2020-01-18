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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

/**
 *
 * @author batoi
 */
public class Controlador_Aplicacion {

    public static Empleado empleado;
    private EmpleadosDAO conexionEmpleados;
    private TareasDAO conexionTareas;
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
    public void initialize() {
        conexionEmpleados = new EmpleadosDAO();
        conexionTareas = new TareasDAO();
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
            System.out.println(n_u);
            String pas = TextField_contrasenya_login.getText();
            System.out.println(pas);
            if (conexionEmpleados.inicio_sesion(n_u, pas)) {
                System.out.println("Sesion no iniciada");
            } else {
                System.out.println("Sesion iniciada");

            }
        } catch (Exception ex) {
        }
    }

    @FXML
    public void insertar_empleado() {
        try {
            conexionEmpleados.insert(coger_informacion_de_empleado());
        } catch (Exception ex) {

        }
    }

    @FXML
    public void modificar_empleado() {

    }

    @FXML
    public void salir_de_la_sesion() {

    }

    @FXML
    public void eliminar_empleado() {

    }

    @FXML
    public void insertar_tarea() {

    }

    private Empleado coger_informacion_de_empleado() {
        Empleado empleado = new Empleado();

        empleado.setDireccion(this.TextField_direccion.getText());

        empleado.setLocalidad(this.TextField_localidad.getText());

        LocalDate localDate = this.fecha_de_nacimiento_login.getValue();

        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date fecha_de_nacimiento = Date.from(instant);
        empleado.setFecha_nacimiento(fecha_de_nacimiento);

        empleado.setNombre(this.TextField_nombre_de_usuario.getText());
        empleado.setContrasenya(this.TextField_contrasenya_login1.getText());
        empleado.setPrimer_apellido(this.TextField_primer_apellido.getText());
        empleado.setSegundo_apellido(this.TextField_segundo_apellido.getText());
        int telefono = 0;

        try {
            telefono = Integer.parseInt(TextField_telefono.getText());
        } catch (NumberFormatException e) {
            telefono = 0;
        }

        empleado.setTelefono(telefono);

        return empleado;
    }

    @FXML
    public void modificar_tarea() {

    }

    @FXML
    public void eliminar_tarea() {

    }

    @FXML
    public void compartir_tarea() {
        ocultar_desocultar("informacion_usuario");
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
            default:
                break;
        }

    }

}
