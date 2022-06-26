package com.arifng.camunda.config;

import org.camunda.bpm.extension.keycloak.plugin.KeycloakIdentityProviderPlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Rana on 29/12/2021.
 */
@Component
@ConfigurationProperties(prefix="plugin.identity.keycloak")
public class KeycloakIdentityProvider extends KeycloakIdentityProviderPlugin {

}
