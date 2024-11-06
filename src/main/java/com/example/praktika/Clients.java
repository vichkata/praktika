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

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Clients extends ListCell<String> {
    @FXML
    private Button about;


    @FXML
    private TextField pos;

    @FXML
    private Button add;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image;
    @FXML
    private ImageView im_shop;
    @FXML
    private ComboBox<String> sea;
    @FXML
    private ComboBox<String> climate;

    @FXML
    private Button main;

    @FXML
    private ListView<Photo> tour;
    public static  int num;

    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/pos.png");
        image.setImage(imag);
        Image imag1 = new Image("C:/Users/Вика/IdeaProjects/course/loading.png");
        image1.setImage(imag1);
        Image imag2 = new Image("C:/Users/Вика/IdeaProjects/course/shop.png");
        im_shop.setImage(imag2);

        db = new DB();
// Получение списка фотографий из базы данных
        List<Photo> ls=db.getTour();
        // Добавление элементов списка в ListView
        tour.getItems().addAll(ls);
        tour();
        poisk();
        combobox();
        main();
        add();
    }

    private void tour(){
        tour.setCellFactory(stringListView -> {
            ListCell<Photo> cell = new Tour();

            return cell;
        });
    }

    private void poisk(){
        pos.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();

            while (!filter.isEmpty()) {

                tour.getItems().clear();
                try {
                    ArrayList<Integer> co = db.getCountry(pos.getText());
                    for (int i=0; i<co.size();i++){
                        ArrayList<Integer> id = db.getHotel(co.get(i));
                        for (int j=0; j<id.size();j++){
                            System.out.println(id);
                            List<Photo> ls = db.getTour(id.get(j));
                            tour.getItems().addAll(ls);}

                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        });
    }

    private void combobox() throws SQLException, ClassNotFoundException {
        List<String> clim = db.getClimate(); // Получаем список пунктов из базы данных
        if (!clim.isEmpty()) {
            ObservableList<String> items = FXCollections.observableArrayList(clim);
            climate.setItems(items);// Добавляем все пункты в выпадающий список
        }

        String more_yes = "есть";
        String more_no = "нет";

        ArrayList<String> se = new ArrayList<>();
        se.add(more_yes);
        se.add(more_no);

        ObservableList<String> items = FXCollections.observableArrayList(se);
        sea.setItems(items);// Добавляем все пункты в выпадающий список



    }

    public void HomePage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        db = new DB();

        ArrayList<Integer> co = db.getClimate(String.valueOf(climate.getValue()));
        tour.getItems().clear();
        try {
            for (int i = 0; i < co.size(); i++) {
                List<Photo> ls = db.getTourClimate(co.get(i));
                tour.getItems().addAll(ls);
                System.out.println(ls);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (sea.getValue().equals("есть")){
            ArrayList<Integer> se = db.getSea();
            tour.getItems().clear();
            try {
                for (int i = 0; i < se.size(); i++) {
                    List<Photo> ls = db.getTourClimate(se.get(i));
                    tour.getItems().addAll(ls);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            ArrayList<Integer> se = db.getSeaNo();
            tour.getItems().clear();
            try {
                for (int i = 0; i < se.size(); i++) {
                    List<Photo> ls = db.getTourClimate(se.get(i));
                    tour.getItems().addAll(ls);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (climate.getValue()!=null && sea.getValue()!=null){
            tour.getItems().clear();
            ArrayList<Integer> c = db.getClimate(String.valueOf(climate.getValue()));//айдишники с выбранной страной

            if (sea.getValue()=="есть"){
                ArrayList<Integer> se = db.getSea();//айдишники стран у которых есть море

                for (int i = 0; i < se.size(); i++){
                    for (int j = 0; j < c.size(); j++){
                        if (se.get(i)==c.get(j)){
                            List<Photo> ls = db.getTourClimate(se.get(i));
                            tour.getItems().addAll(ls);
                        }
                    }
                }
            }
            else {
                ArrayList<Integer> se = db.getSeaNo();//айдишники стран у которых нет море
                for (int i = 0; i < se.size(); i++){
                    for (int j = 0; j < c.size(); j++){
                        if (se.get(i)==c.get(j)){
                            List<Photo> ls = db.getTourClimate(se.get(i));
                            tour.getItems().addAll(ls);
                        }
                    }
                }
            }
        }
    }

    private void main() {
        main.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cancel.fxml")); //для клиентов и менеджера
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                    // Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }   catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void add() {
        add.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addrev.fxml")); //для клиентов и менеджера
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                    // Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }   catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void Home(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        tour.getItems().clear();
        // Получение списка фотографий из базы данных
        List<Photo> ls=db.getTour();
        // Добавление элементов списка в ListView
        tour.getItems().addAll(ls);
        tour();
    }

    public void Home_shop(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("shop.fxml")); //для клиентов и менеджера
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
            // Установка иконки для главного окна приложения
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.show();
        }   catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
