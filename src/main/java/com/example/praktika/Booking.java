package com.example.praktika;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Booking {
    @FXML
    private ImageView image;

    @FXML
    private ComboBox<String> clients;

    @FXML
    private TextField count;
    @FXML
    private DatePicker dateP;
    @FXML
    private Label error;

    @FXML
    private Button booking;

    private int id_tour;
    private double price;
    public String x;

    DB db = null;
    DBuser dBuser = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException, ParseException {
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/booking.png");
        image.setImage(imag);

        // Установка начальной даты
        dateP.setValue(LocalDate.now());
        dateP.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                // Запрещаем выбор даты, которая раньше текущей даты
                setDisable(empty || date.compareTo(LocalDate.now()) < 1);
            }
        });

        // Инициируем объект
        db = new DB();
        dBuser = new DBuser();

        ArrayList<String> se = dBuser.getUsers();

        ObservableList<String> items = FXCollections.observableArrayList(se);
        clients.setItems(items);// Добавляем все пункты в выпадающий список

        booking();
    }
    private void booking() {
        booking.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (clients.getValue().isEmpty() || count.getText().isEmpty() || dateP==null){
                        showAlert("", "Вы вписали не все данные!");
                    }
                    dBuser.getIdName(clients.getValue());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date_f;
                    date_f = dateFormat.parse(String.valueOf(dateP.getValue()));

                    id_tour = Employee.num;

                    price = db.getPriceTour(id_tour) *Integer.parseInt(count.getText());
                    System.out.println(db.getPriceTour(id_tour));

                    java.sql.Date sqlDate_f = new java.sql.Date(date_f.getTime());
                    db.insertProduct(dBuser.getIdName(clients.getValue()),id_tour, Integer.parseInt(count.getText()), BigDecimal.valueOf(price), sqlDate_f);
                    showAlert("", "Вы забронировали тур!");

                }  catch (SQLException e) {

                    x = e.getMessage();
                    error.setText(x);
                    System.out.println(x);
                }
                catch (ParseException  e){
                    throw new RuntimeException(e);
                }
                catch (ClassNotFoundException e){
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
}

