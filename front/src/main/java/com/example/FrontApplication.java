package com.example;

import com.example.front.views.ModKeyView;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan(basePackages = {"com.example.dal.mapper"})
@SpringBootApplication
public class FrontApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(FrontApplication.class, ModKeyView.class, args);
    }
}
