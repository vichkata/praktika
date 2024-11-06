package com.example.praktika;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Tour extends ListCell<Photo> {
    @FXML
    private AnchorPane idContainer;
    @FXML
    private Label disc;

    @FXML
    private Label idFio;

    @FXML
    private ImageView idImg;

    @FXML
    private HBox idStr;

    @FXML
    private Label stok;

    private FXMLLoader mLLoader;


    @Override
    protected void updateItem(Photo student, boolean empty) {
        super.updateItem(student, empty);

        if(empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("tour.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            idFio.setText(student.getname_tour());
            disc.setText(student.getdescription_tour());
            stok.setText(String.valueOf(student.getprice_tour()));

            if (student.photo() != null) {
                File file = new File(student.photo());
                try {
                    String urlImage = file.toURI().toURL().toString();
                    Image image = new Image(urlImage);
                    System.out.println(image);
                    idImg.setImage(image);
                } catch (MalformedURLException ignored) {
                }

                setText(null);
                setGraphic(idContainer);
            }
            }
        }
    }


