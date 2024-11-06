package com.example.course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Register {

    @FXML
    private TextField email;

    @FXML
    private TextField fio;

    @FXML
    private ImageView image;

    @FXML
    private TextField login;

    @FXML
    private TextField pas;

    @FXML
    private Button reg;

    @FXML
    private ComboBox<String> role;

    @FXML
    private Label Suc;

    DBuser dBuser = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/src/user.png");
        image.setImage(imag);

        // Инициируем объект
        dBuser = new DBuser();
        List<String> tasks = dBuser.getRole(); // Получаем список пунктов из базы данных
        if (!tasks.isEmpty()) {
            ObservableList<String> items = FXCollections.observableArrayList(tasks);
            role.setItems(items);// Добавляем все пункты в выпадающий список
        }


        reg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (fio.getText().isEmpty() || login.getText().isEmpty() || pas.getText().isEmpty() || role.getValue().isEmpty() || email.getText().isEmpty()){
                        showAlert("", "Вы вписали не все данные!");
                    }
                    Suc.setText(dBuser.insertClient(fio.getText(), login.getText(), pas.getText(), dBuser.getName(role.getValue()), email.getText()));
                    //dBuser.insertProduct(fio.getText(), login.getText(), pas.getText(), email.getText(), dBuser.getName(role.getValue()) );
                }
                catch (Exception e) {
                    e.printStackTrace();
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
}
