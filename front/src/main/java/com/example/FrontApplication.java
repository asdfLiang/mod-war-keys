package com.example;

import com.example.front.views.ModKeyView;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

import javafx.stage.Stage;

import org.springframework.boot.autoconfigure.SpringBootApplication;

// @MapperScan(basePackages = {"com.example.dal.mapper"})
@SpringBootApplication
public class FrontApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(FrontApplication.class, ModKeyView.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        stage.resizableProperty().setValue(Boolean.FALSE);
    }
}
