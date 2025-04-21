module com.animeapi.animeapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.annotation;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.animeapi.animeapi to javafx.fxml;
    exports com.animeapi.animeapi;
    exports com.animeapi.animeapi.payload;
    exports com.animeapi.animeapi.ressources;

}