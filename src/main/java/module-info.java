module com.example.praktika {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.praktika to javafx.fxml;
    exports com.example.praktika;
}