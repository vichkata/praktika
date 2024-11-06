package com.example.praktika;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Cancel {
    @FXML
    private Button cansl;

    @FXML
    private ListView<String> discr;
    @FXML
    private Label mess;

    @FXML
    private TextField reson;

    @FXML
    private ImageView image;

    private int idusers;
    DB db = null;
    DBuser dBuser = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException, ParseException {

        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/image.jpg");
        image.setImage(imag);

        db = new DB();
        dBuser = new DBuser();
        idusers = db.getIdUsers();
        ArrayList<String> ls = dBuser.getBookingUsers(idusers);
        AbstractList<String> discription = new ArrayList<>();

        for (int i = 0; i < ls.size(); i++) {
            discription.add(dBuser.getDescUsers(Integer.parseInt(ls.get(i))));
        }
        // Добавление элементов списка в ListView
        discr.getItems().addAll(discription);

        while (!reson.getText().isEmpty()) {
            mess.setText("Вы не заполнили текст");
        }
        cansl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (discr.getItems().isEmpty() || reson.getText().isEmpty()){
                        showAlert("", "Вы вписали не все данные!");
                    }
                    System.out.println( discr.getSelectionModel().getSelectedItem());
                    proverca();
                    dBuser.deleteProduct(idusers, dBuser.getIdDescUsers(String.valueOf(discr.getSelectionModel().getSelectedItem())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void proverca() throws SQLException, ClassNotFoundException, ParseException {

        System.out.println(db.getData(dBuser.getIdBooking(idusers)));
        Date datainput=db.getData(dBuser.getIdBooking(idusers));


        // Получение текущей даты
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);
        // Сравнение дат
        if (datainput.equals(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
            cansl.setDisable(false);
            mess.setText("Вы не можите отменить бронь, потому что дата тура сегодня");
        }
    }
}

