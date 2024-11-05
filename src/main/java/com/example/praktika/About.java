package com.example.praktika;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.String.valueOf;

public class About {

    @FXML
    private Label count;

    @FXML
    private GridPane country;

    @FXML
    private Label description;

    @FXML
    private Label name;
    @FXML
    private ImageView home;

    @FXML
    private Label price;

    @FXML
    private ImageView image;

    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/src/trevel2.jpg");
        image.setImage(imag);

        Image hom = new Image("C:/Users/Вика/IdeaProjects/course/src/home.png");
        home.setImage(hom);

        // Инициируем объект
        db = new DB();
        ArrayList<String> coun = new ArrayList<>(db.getCountry());
        for (int i =0; i<coun.size(); i++ ) {
            Label s = new Label( coun.get(i));
            // Настраиваем стиль элемента
            s.setStyle("-fx-padding: 15px; -fx-border-color: black;");
            //настраеваем штрифт элементка и размер
            Font font = Font.font("Comic Sans MS", FontWeight.BOLD, 15);
            s.setFont(font);

            System.out.println(s.getText());
            country.setColumnIndex(s, i);
            country.getChildren().add(s);
        }
        tour();
    }

    public void tour() throws SQLException, ClassNotFoundException {
        int n = (int)(Math.random()*db.getMaxTour()-1)+1;

        System.out.println("Чило"+n);
        if (n==2){n=n+1;}
        name.setText(String.valueOf(db.getName(n)));
        description.setText(db.getDescription(n));

        int num = (db.getCount(n));
        count.setText(db.getNamaCount(num));
        price.setText(String.valueOf(db.getPrice(n)));
    }

    public void HomePage(MouseEvent mouseEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
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


