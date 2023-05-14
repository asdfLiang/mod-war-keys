package com.example.front.controller.action;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @since 2023/5/14 10:50
 * @author by liangzj
 */
public class AlertAction {

    public static void info(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void error(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

    public static ButtonType confirm(String confirmMsg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(confirmMsg);
        Optional<ButtonType> buttonType = alert.showAndWait();

        return buttonType.orElse(ButtonType.CANCEL);
    }
}
