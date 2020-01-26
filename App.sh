#!/bin/bash
ruta='.'
lib="lib"
nombre_jar="Actividad3-1.0-SNAPSHOT.jar"
cd $ruta
java --module-path "$ruta/$lib" --add-modules=javafx.controls,javafx.fxml -jar "$ruta/$nombre_jar"