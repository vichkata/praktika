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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Authorization {

    @FXML
    private ImageView image;

    @FXML
    private TextField log;

    @FXML
    private TextField pas;

    @FXML
    private Button vhod;

    @FXML
    private Button vhod_g;

    @FXML
    private Button register;

    public static int id_rol;
    public static int id_cli;

    DB db = null;
    DBuser dBuser = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/src/avtoriz-transformed.jpg");
        image.setImage(imag);

        // Инициируем объект
        db = new DB();
        dBuser = new DBuser();

        voiti();
        voiti_g();
        register();
    }

    private void voiti() {
        vhod.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (!log.getText().trim().equals("") & !pas.getText().trim().equals("")) {
                        id_rol=dBuser.getIdRole(log.getText(), pas.getText());
                        int n = db.getUsers(log.getText(), pas.getText());
                        if (n==0){

                            System.out.println("Вы ввели неверный пароль");
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("captcha.fxml")); //для клиентов и менеджера
                            Scene scene = new Scene(fxmlLoader.load(), 450, 200);
                            Stage stage = new Stage();
                            stage.setTitle("Капча");
                            Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                            // Установка иконки для главного окна приложения
                            stage.getIcons().add(icon);
                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false);
                            stage.show();
                        }

                        if (n != 0) {
                            id_cli = dBuser.getIdUser(log.getText(), pas.getText());
                            System.out.println(id_cli);
                            if (id_rol > 1) {
                                showAlert("", "Вы зашли как работник!");
                                System.out.println("Вы ввели верный пароль");
                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("employee.fxml")); //для клиентов и менеджера
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
                            else {
                                showAlert("", "Вы зашли как клиент!");
                                System.out.println("Вы ввели верный пароль");
                                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("clients.fxml")); //для клиентов и менеджера
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
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void voiti_g() {
        vhod_g.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println("Вы вшли как гость");
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml")); //для клиентов и менеджера
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void register() {
        register.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println("Вы вшли как гость");
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml")); //для клиентов и менеджера
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                    // Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
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
    @FXML
    public void close() {
        Stage stage = (Stage) vhod.getScene().getWindow();    stage.close();
    }


}
