module StrongHold {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires java.xml.crypto;
    requires java.desktop;
    requires org.controlsfx.controls;
    requires com.auth0.jwt;

    exports Server.model;
    opens Server.model to com.google.gson;
    exports Server.model.Items;
    opens Server.model.Items to com.google.gson;
    exports Server.model.People;
    opens Server.model.People to com.google.gson;
    exports Server.model.MapCellItems;
    opens Server.model.MapCellItems to com.google.gson;
    exports Server.model.Buildings;
    opens Server.model.Buildings to com.google.gson;
    exports Server.model.MapGeneration;
    opens Server.model.MapGeneration to com.google.gson;
    exports Server.enums.Messages;
    exports Client.view;
    exports Client;
    exports Server.enums.Types;
    exports Client.model;
    opens Client.model to com.google.gson;
    opens Client.view to javafx.fxml;
}