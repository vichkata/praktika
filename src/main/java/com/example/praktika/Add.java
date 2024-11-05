package com.example.praktika;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Add {

    @FXML
    private TextField desc;

    @FXML
    private ImageView image;
    @FXML
    private ImageView home;

    @FXML
    private Button add;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private ComboBox<String> hotel;

    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/add.png");
        image.setImage(imag);
        Image hom = new Image("C:/Users/Вика/IdeaProjects/course/src/home.png");
        home.setImage(hom);

        db = new DB();
        List<String> clim = db.getHotel(); // Получаем список пунктов из базы данных
        if (!clim.isEmpty()) {
            ObservableList<String> items = FXCollections.observableArrayList(clim);
            hotel.setItems(items);// Добавляем все пункты в выпадающий список
        }
        add.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (price.getText().isEmpty() || desc.getText().isEmpty() || name.getText().isEmpty() || hotel.getValue().isEmpty()){
                        showAlert("", "Вы вписали не все данные!");
                    }
                    db.insertTour(name.getText(), desc.getText(), Integer.parseInt(price.getText()), db.getIdHotel(hotel.getValue()));
                    showAlert("", "Вы добавили тур!");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Stage stage = (Stage)
                alert.getDialogPane().getScene().getWindow();
        Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
        // Установка иконки для главного окна приложения
        stage.getIcons().add(icon);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void HomePage(MouseEvent mouseEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("employee.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
        // Установка иконки для главного окна приложения
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        close();
    }

    @FXML
    public void close() {
        Stage stage = (Stage) home.getScene().getWindow();    stage.close();
    }
}



