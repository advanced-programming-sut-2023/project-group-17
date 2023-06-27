module StrongHold {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires java.xml.crypto;
    requires java.desktop;
    requires org.controlsfx.controls;

    exports Model;
    opens Model to com.google.gson;
    exports Model.Items;
    opens Model.Items to com.google.gson;
    exports Model.People;
    opens Model.People to com.google.gson;
    exports Model.MapCellItems;
    opens Model.MapCellItems to com.google.gson;
    exports Model.Buildings;
    opens Model.Buildings to com.google.gson;
    exports Model.MapGeneration;
    opens Model.MapGeneration to com.google.gson;
    exports Server.enums.Messages;
    exports Client.view;
    exports Client;
    exports Server.enums.Types;
}