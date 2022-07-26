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
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author batoi
 */
public class Controlador_Aplicacion {

    public static Empleado empleado;
    public static Tarea tarea_seleccionada;
    private ArrayList<Tarea> tareas_lista;
    private EmpleadosDAO conexionEmpleados;
    private TareasDAO conexionTareas;
    public static boolean subtarea = false;
    public static boolean nuevo_usuario = false;
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

            for (Empleado empleado : conexionEmpleados.findAll()) {
                System.out.println(empleado.getNombre() + " " + empleado.getContrasenya());
            }

            if (nuevo_usuario) {
                coger_o_poner_informacion_de_empleado(false);
            }

            if (empleado != null) {
                actualizar_vista_usuario();
            } else {
                ocultar_desocultar("login");
            }
        } catch (Exception ex) {

        }

    }

    @FXML
    public void iniciar_sesion() {
        try {
            if (conexionEmpleados.inicio_sesion(TextField_nombre_de_usuario_login.getText(), TextField_contrasenya_login.getText())) {
                ocultar_desocultar("todo");
                actualizar_vista_usuario();
            } else {

            }
        } catch (Exception ex) {

        }
    }

    public void actualizar_vista_usuario() {
        try {
            ArrayList<Tarea> lista_tareas = empleado.getLista_tareas();
            Collections.sort(lista_tareas);
            TextField_nombre_de_usuario_login.setText(empleado.getNombre());
            ComboBox_Empleados_Compartir.getItems().setAll(conexionEmpleados.findAll());
            tareas_lista = Llenar_lista_tareas(lista_tareas);
            Llenar_tree();
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

    @FXML
    public void Llenar_tareas_y_subtareas() {
        ComboBox_Empleados_subtareas.getItems().setAll(empleado.getLista_tareas());
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

        try {
            System.out.println(treeview_vista.getSelectionModel().getSelectedIndex() - 1);
            tarea_seleccionada = new Tarea(tareas_lista.get(treeview_vista.getSelectionModel().getSelectedIndex() - 1));
            coger_o_poner_informacion_de_la_tarea(false);
        } catch (Exception ex) {

        }
    }

    public void Llenar_tree() {
        try {
            treeview_vista.setRoot(crear_vista_arbol(empleado.getLista_tareas()));
            treeview_vista.setRoot(treeview_vista.getTreeItem(0));
        } catch (Exception ex) {

        }
    }

    @FXML
    public void Anyadir_una_subtarea() {
        try {
            Tarea tarea = empleado.getLista_tareas().get(ComboBox_Empleados_subtareas.getSelectionModel().getSelectedIndex());
            tarea_seleccionada = new Tarea(coger_o_poner_informacion_de_la_tarea(true));
            tarea_seleccionada.setId(0);
            conexionTareas.insert(tarea_seleccionada);
            tarea.getLista_subtareas().add(tarea_seleccionada);
            actualizar_vista_usuario();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
    }

    private TreeItem<Tarea> crear_vista_arbol(ArrayList<Tarea> lista_tareas) {
        TreeItem<Tarea> rootItem = new TreeItem();
        try {
            rootItem.setExpanded(true);
            for (Tarea tarea : lista_tareas) {
                if ((!tarea.equals(new Tarea())) && (tarea.esta_entre(new Date()))) {
                    TreeItem<Tarea> item = new TreeItem();
                    item.setValue(tarea);
                    rootItem.getChildren().add(item);
                    tarea.getLista_subtareas().removeIf(n -> (n.equals(tarea)));
                    if ((!tarea.getLista_subtareas().isEmpty())) {   
                        try {
                            rootItem.getChildren().add(crear_vista_arbol(tarea.getLista_subtareas()));
                        } catch (StackOverflowError ex) {

                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        return rootItem;
    }

    @FXML
    public void insertar_tarea() {

        try {
            Tarea tarea = coger_o_poner_informacion_de_la_tarea(true);
            conexionTareas.insert(tarea);
            empleado.getLista_tareas().add(tarea);
            conexionEmpleados.update(empleado);
            actualizar_vista_usuario();
            subtarea = false;
        } catch (Exception ex) {

        }
    }

    @FXML
    public void poner_columnas() {
        try {
            App.Cambiar_Pantalla("perfil");
            nuevo_usuario = true;
        } catch (IOException ex) {

        }
    }

    @FXML
    public void poner_perfil() {
        try {
            App.Cambiar_Pantalla("perfil");
            nuevo_usuario = false;
        } catch (IOException ex) {

        }
    }

    private Empleado coger_o_poner_informacion_de_empleado(boolean coger) {
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
        } catch (Exception e) {

        }

        return Empleado_creado;
    }

    private Tarea coger_o_poner_informacion_de_la_tarea(boolean coger) {
        Tarea Tarea_creada = null;

        try {
            if (coger) {
                Tarea_creada = new Tarea();
                if (tarea_seleccionada != null) {
                    Tarea_creada.setId(tarea_seleccionada.getId_tarea());
                    Tarea_creada.setLista_subtareas(tarea_seleccionada.getLista_subtareas());
                }
                Tarea_creada.setDescripcion(TextArea_Descripcion.getText());
                Tarea_creada.setEsta_realizada(CheckBox_Realizada_la_tarea.isSelected());
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
        } catch (Exception ex) {

        }

        return Tarea_creada;
    }

    @FXML
    public void modificar_tarea() {
        try {
            conexionTareas.update(coger_o_poner_informacion_de_la_tarea(true));
            ComboBox_Empleados_Compartir.getItems().setAll(conexionEmpleados.findAll());
            actualizar_vista_usuario();
        } catch (Exception ex) {

        }
    }

    @FXML
    public void eliminar_tarea() {
        try {
            conexionTareas.delete(tarea_seleccionada.getId_tarea());

            for (Empleado empleado : this.ComboBox_Empleados_Compartir.getItems()) {
                empleado.getLista_tareas().remove(tarea_seleccionada);
            }

            actualizar_vista_usuario();
            App.Cambiar_Pantalla("tareas.fxml");
        } catch (Exception ex) {

        }
    }

    @FXML
    public void compartir_tarea() {
        Empleado Otro_Empleado = new Empleado(ComboBox_Empleados_Compartir.getSelectionModel().getSelectedItem());
        Tarea tarea = new Tarea(tarea_seleccionada);
        Otro_Empleado.getLista_tareas().add(tarea);
        try {
            conexionEmpleados.update(Otro_Empleado);
        } catch (Exception ex) {

        }
    }

    public void poner_escritorio(boolean escritorio) {
        try {
            for (Node elemento : this.escritorio.getChildren()) {
                elemento.setVisible(escritorio);

                if (elemento.getClass().getCanonicalName().contains("GridPane")) {
                    GridPane gridpane = (GridPane) elemento;
                    gridpane.setVisible(true);
                    for (Node childrens : gridpane.getChildren()) {
                        childrens.setVisible(escritorio);
                    }

                }
            }
        } catch (Exception ex) {

        }
    }

    public void ocultar_desocultar(String accion) {

        try {
            poner_escritorio(false);
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
                    bt_parte_entrar.setVisible(true);
                    bt_parte_salir.setVisible(true);
                    bt_parte_agregar.setVisible(true);
                    break;
                case "todo":
                    poner_escritorio(true);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {

        }

    }

    private ArrayList<Tarea> Llenar_lista_tareas(ArrayList<Tarea> lista_tareas) {
        ArrayList<Tarea> rootItem = new ArrayList();
        try {
            for (Tarea tarea : lista_tareas) {
                if ((!tarea.equals(new Tarea())) && (tarea.esta_entre(new Date()))) {
                    rootItem.add(tarea);
                    tarea.getLista_subtareas().removeIf(n -> (n.equals(tarea)));
                    if (tarea.getLista_subtareas().size() >= 0) {
                        try {
                            //rootItem.add(new Tarea());
                            rootItem.addAll(Llenar_lista_tareas(tarea.getLista_subtareas()));
                        } catch (StackOverflowError ex) {

                        }

                    }
                }
            }
        } catch (Exception ex) {

        }
        return rootItem;
    }
}
