package com.example;

import com.example.front.views.ModKeyView;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

import javafx.stage.Stage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(
        scanBasePackages = {
            "com.example.front",
            "com.example.service",
            "com.example.integrate",
            "com.example.dal",
        })
@MapperScan(basePackages = {"com.example.dal.mapper"})
@PropertySource("classpath:application.properties")
public class FrontApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(FrontApplication.class, ModKeyView.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        stage.setTitle("war3改键器");
        stage.resizableProperty().setValue(Boolean.FALSE);
    }
}
