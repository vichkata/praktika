package com.example.praktika;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Addrev {
    @FXML
    private TextField add;
    @FXML
    private Button insert;
    @FXML
    private TextField star;
    @FXML
    private ImageView home;

    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image hom = new Image("C:/Users/Вика/IdeaProjects/course/src/home.png");
        home.setImage(hom);
        // Инициируем объект
        db = new DB();
        insert.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    // Метод, что будет срабатывать
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            if (add.getText().isEmpty() || star.getText().isEmpty()){
                                showAlert("", "Вы вписали не все данные!");
                            }
                            db.insertReviews(Authorization.id_cli, add.getText(), Integer.parseInt(star.getText()));
                            showAlert("", "Вы добавили отзыв!");

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

        close();
    }

    public void HomePage(MouseEvent mouseEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("clients.fxml"));
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

