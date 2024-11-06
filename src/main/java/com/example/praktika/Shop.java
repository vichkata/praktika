package com.example.praktika;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    @FXML
    private ListView<Photo> stud;

    DB db = null;
    private int iduser;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        db = new DB();

        iduser = db.getIdUsers();
        ArrayList<Integer> id_tour = new ArrayList<>(db.getIdTourU(iduser));

        for(int i=0; i<id_tour.size();i++){
            List<Photo> ls = db.getTourId(id_tour.get(i));
            // Добавление элементов списка в ListView
            stud.getItems().addAll(ls);
        }

        stud();
    }
    private void stud(){
        stud.setCellFactory(stringListView -> {
            ListCell<Photo> cell = new Tour();

            return cell;
        });
    }
}
