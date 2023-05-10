package com.example;

import com.example.front.views.ModKeyView;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

import javafx.stage.Stage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @PropertySource(value = {"file:D:\\liangzj\\Documents\\translation.properties"})
@MapperScan(basePackages = {"com.example.dal.mapper"})
@SpringBootApplication(
        scanBasePackages = {
            "com.example.front",
            "com.example.back",
            "com.example.transaction",
            "com.example.dal",
        })
public class FrontApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(FrontApplication.class, ModKeyView.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        stage.setTitle("war3hotKey");
        stage.resizableProperty().setValue(Boolean.FALSE);
    }
}
