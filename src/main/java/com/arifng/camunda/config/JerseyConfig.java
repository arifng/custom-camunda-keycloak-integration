package com.arifng.camunda.config;

import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Rana on 21/11/2021.
 */
@Component
@ApplicationPath("/rest")
public class JerseyConfig extends CamundaJerseyResourceConfig {

    @Override
    protected void registerAdditionalResources() {
    }

}
