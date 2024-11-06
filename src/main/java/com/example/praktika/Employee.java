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
import java.util.ArrayList;
import java.util.List;

public class Employee extends ListCell<String> {
    @FXML
    private Button about;


    @FXML
    private TextField pos;
    @FXML
    private ImageView image1;

    @FXML
    private Button add;
    @FXML
    private ImageView image;
    @FXML
    private ComboBox<String> sea;
    @FXML
    private ComboBox<String> climate;

    @FXML
    private Button main;

    @FXML
    private ListView<Photo> tour;
    public static  int num;
    public  static int id_tour;

    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image imag = new Image("C:/Users/Вика/IdeaProjects/course/pos.png");
        image.setImage(imag);

        Image imag1 = new Image("C:/Users/Вика/IdeaProjects/course/loading.png");
        image1.setImage(imag1);

        db = new DB();
// Получение списка фотографий из базы данных
        List<Photo> ls=db.getTour();
        // Добавление элементов списка в ListView
        tour.getItems().addAll(ls);
        tour();
        poisk();
        combobox();
        add();
    }

    private void tour(){
        tour.setCellFactory(stringListView -> {
            ListCell<Photo> cell = new Tour();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Бронировать");
            editItem.setOnAction(event -> {
                Photo item = cell.getItem();
                try {
                    num = db.getIdTour(item.getname_tour());
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("booking.fxml"));
                    Stage stage = new Stage();
                    Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                    // Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            MenuItem editItemD = new MenuItem("Удалить");
            editItemD.setOnAction(event -> {
                int id =0;
                Photo item = cell.getItem();
                try {
                    id = db.getIdTour(item.getname_tour());
                    System.out.println(id);
                    db.deleteTour(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            MenuItem editItemE = new MenuItem("Редактировать");
            editItemE.setOnAction(event -> {
                Photo item = cell.getItem();
                try {
                    id_tour = db.getIdTour(item.getname_tour());
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editing.fxml"));
                    Stage stage = new Stage();
                    Image icon = new Image("C:/Users/Вика/IdeaProjects/course/icons.png");
                    // Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
                contextMenu.getItems().addAll(editItem, editItemD,editItemE);
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
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

        public void add(){
            add.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                // Метод, что будет срабатывать
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add.fxml")); //для клиентов и менеджера
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

    public void Home(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        tour.getItems().clear();
        // Получение списка фотографий из базы данных
        List<Photo> ls=db.getTour();
        // Добавление элементов списка в ListView
        tour.getItems().addAll(ls);
        tour();
    }
}