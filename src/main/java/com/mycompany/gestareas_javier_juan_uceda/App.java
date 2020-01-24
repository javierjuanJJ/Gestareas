package com.mycompany.gestareas_javier_juan_uceda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage Stage;

    /**
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("tareas.fxml"));
        stage.setScene(scene);
        Stage = stage;
        Stage.setTitle("Pantalla inicial");
        Stage.show();

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void Cambiar_Pantalla(String archivo_fxml) throws IOException {
        
        scene = new Scene(loadFXML((archivo_fxml.equals("perfil")) ? "perfil_usuario.fxml" : "tareas.fxml" ));
        Stage.setScene(scene);
        Stage.setTitle("Pantalla inicial");
        Stage.show();

    }
}
