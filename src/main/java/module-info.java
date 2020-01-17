module com.mycompany.gestareas_javier_juan_uceda {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.gestareas_javier_juan_uceda to javafx.fxml;
    exports com.mycompany.gestareas_javier_juan_uceda;
    requires neodatis.odb;
}