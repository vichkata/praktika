package com.example.praktika;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class CaptchaController {
    private static final int captchaLength = 5;

    @FXML
    private Canvas canvas;

    @FXML
    private Button prov;

    @FXML
    private TextField textInputField;
    private CaptchaGenerator captchaGenerator;
    private String captchaText;



    public boolean validate() {
        if (!Objects.equals(this.captchaText, this.textInputField.getText())) {
            regenerateCaptcha();
            this.textInputField.setText("");
            return false;
        }

        return true;
    }

    @FXML
    private void initialize() {
        this.captchaGenerator = new CaptchaGenerator(this.canvas);

        regenerateCaptcha();

        String captchatext = captchaGenerator.captchatext;//текст который на капче

        prov.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    if (textInputField.getText().equals(captchatext)) {
                        // Закрытие текущего окна
                        Stage currentStage = (Stage) prov.getScene().getWindow();
                        currentStage.close();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                rbar();
            }
        });
    }

    private void regenerateCaptcha() {

        this.captchaText = this.captchaGenerator.generate(captchaLength);
    }

    public void rbar() {
        try {
            try {
                Thread.sleep(10000); // Задержка в 10 секунд (10000 миллисекунд)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Stage currentStage = (Stage) prov.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}


