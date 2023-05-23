package com.example.back.support.transaction;

import com.example.back.support.enums.EnvEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @since 2023/5/23 22:40
 * @author by liangzj
 */
@Component
public class PropertiesFactory {

    @Autowired private List<PropertiesTemplate> handlers;

    public PropertiesTemplate getTemplate(String env) {
        EnvEnum envEnum = EnvEnum.from(env);

        return handlers.stream()
                .filter(handler -> Objects.equals(envEnum, handler.env()))
                .findFirst()
                .orElseThrow();
    }
}
