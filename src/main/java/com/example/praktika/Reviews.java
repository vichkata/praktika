package com.example.course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class Reviews {
    @FXML
    private ImageView image;

    @FXML
    private ListView<String> rew;
    @FXML
    private BorderPane border;
    @FXML
    private ListView<ImageView> star;
    @FXML
    private GridPane table;
    @FXML
    private ImageView home;
    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/review.png");
        image.setImage(imag);

        Image hom = new Image("C:/Users/Вика/IdeaProjects/course/src/home.png");
        home.setImage(hom);

        ArrayList<String> rewi = db.getReviews();
        //rew.getItems().addAll(rewi);
        //rew.setStyle("-fx-font-size: " + 20 + "px;");

        // Создаем список фотографий
        ArrayList<ImageView> image_star = new ArrayList<>();
        ArrayList<Integer> sta = db.getStar();
        for (int i = 0; i< sta.size(); i++){
            if (sta.get(i)==5){
                image_star.add(createImageView("star5.png"));
            } if (sta.get(i)==4){
                image_star.add(createImageView("star4.png"));
            }
            if (sta.get(i)==3){
                image_star.add(createImageView("star3.png"));
            }if (sta.get(i)==2){
                image_star.add(createImageView("star2.png"));
            }if (sta.get(i)==1){
                image_star.add(createImageView("star1.png"));
            }if (sta.get(i)==0){
                image_star.add(createImageView("star0.png"));
            }
        }
       // star.getItems().addAll(image_star);
        // Создаем первый столбец
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        column1.setPercentWidth(50); // Установите нужную ширину столбца (в процентах)
        table.getColumnConstraints().add(column1);

        table.setStyle("-fx-font-size: " + 15 + "px;");
// Создаем второй столбец
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50); // Установите нужную ширину столбца (в процентах)
        table.getColumnConstraints().add(column2);


// Заполняем первый столбец
        for (int i = 0; i < rewi.size(); i++) {
            Text text = new Text(rewi.get(i));
            table.add(text, 0, i);
//            Label label = new Label(rewi.get(i));
//            table.add(label, 0, i); // Добавляем метку в первый столбец и i-тую строку
        }

// Заполняем второй столбец
        for (int i = 0; i < image_star.size(); i++) {
            ImageView imageView = image_star.get(i);
            table.add(imageView, 1, i); // Добавляем изображение во второй столбец и i-тую строку
        }
        border.setLeft(new ScrollPane(table));

    }
    private ImageView createImageView(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        String imagePath = classLoader.getResource(filePath).toExternalForm();
        // Создаем объект Image с заданным путем к фотографии
        Image image = new Image(imagePath);

        // Создаем объект ImageView с заданной фотографией
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35); // Устанавливаем высоту ImageView
        imageView.setPreserveRatio(true); // Задаем сохранение пропорций фотографии

        return imageView;
    }

    public void HomePage(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
        // Установка иконки для главного окна приложения
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

    }
}
