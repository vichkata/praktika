package com.example.praktika;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class HelloController {
    @FXML
    private Button about;

    @FXML
    private ImageView image;

    @FXML
    private Button avtor;

    @FXML
    private Button reviews;


    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/src/trev.jpg");
        image.setImage(imag);

        // Инициируем объект
        db = new DB();

        avtor();
        about();
        reviews();
    }

    private void avtor() {
        avtor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("autorization.fxml")); //для клиентов и менеджера
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                    // Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void about() {
        about.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("about.fxml")); //для клиентов и менеджера
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                    // Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void reviews() {
        reviews.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reviews.fxml")); //для клиентов и менеджера
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                    // Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}