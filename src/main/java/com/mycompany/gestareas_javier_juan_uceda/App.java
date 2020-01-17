package com.mycompany.gestareas_javier_juan_uceda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage Stage;

    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("tareas"));
        stage.setScene(scene);
        Stage = stage;
        Stage.setTitle("Pantalla inicial");
        Stage.show();

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public void mensajeExcepcion(Exception ex, String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error de excepción");
        alert.setHeaderText(msg);
        alert.setContentText(ex.getMessage());

        String exceptionText = "";
        StackTraceElement[] stackTrace = ex.getStackTrace();
        for (StackTraceElement ste : stackTrace) {
            exceptionText = exceptionText + ste.toString() + System.getProperty("line.separator");
        }

        Label label = new Label("La traza de la excepción ha sido: ");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public void mensajeConfirmacion(String Titulo, String msg, String tipo) {
        String exceptionText = "";
        StringBuilder exception = new StringBuilder();
        exception.append(System.lineSeparator());
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Exito de la operacion");
        alert.setHeaderText(msg);

        Label label = new Label("La operacion ha sido un exito");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

}
