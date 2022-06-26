package com.arifng.camunda.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Rana on 29/12/2021.
 */
@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:application.yaml")
@Getter
@Setter
public class AppProperties {
    @Value("${rest.security.required-audience:}")
    private String requiredAudience;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri:}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id:}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret:}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.redirect-uri:}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.scope:}")
    private String[] scope;

    @Value("${spring.security.oauth2.client.provider.keycloak.jwk-set-uri:}")
    private String jwkSetUri;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri:}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.provider.keycloak.authorization-uri:}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.provider.keycloak.user-info-uri:}")
    private String userInfoUri;

    @Value("${spring.security.oauth2.client.provider.keycloak.user-name-attribute:}")
    private String userNameAttribute;

}
