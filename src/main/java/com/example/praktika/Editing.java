package com.example.praktika;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Editing {
    @FXML
    private TextField disc;

    @FXML
    private Button edit;

    @FXML
    private ComboBox<String> hotel;

    @FXML
    private ImageView image;

    @FXML
    private TextField name;

    @FXML
    private TextField pricec;

    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/edit.png");
        image.setImage(imag);


        db = new DB();

        name.setText(db.getName(Employee.id_tour));
        disc.setText(db.getDescription(Employee.id_tour));
        pricec.setText(String.valueOf(db.getPrice(Employee.id_tour)));

        List<String> clim = db.getHotel(); // Получаем список пунктов из базы данных
        if (!clim.isEmpty()) {
            ObservableList<String> items = FXCollections.observableArrayList(clim);
            hotel.setItems(items);// Добавляем все пункты в выпадающий список
        }
        edit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    db.updateTour(name.getText(),disc.getText(), Integer.parseInt(pricec.getText()), db.getIdHotel(hotel.getValue()), Employee.id_tour);
                    showAlert("", "Вы отредактировали тур!");
                    close();
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
    @FXML
    public void close() {
        Stage stage = (Stage) edit.getScene().getWindow();    stage.close();
    }
}
