package com.arifng.camunda.config;

import com.arifng.camunda.sso.AudienceValidator;
import com.arifng.camunda.sso.KeycloakAuthenticationFilter;
import com.arifng.camunda.sso.KeycloakAuthenticationProvider;
import com.arifng.camunda.sso.KeycloakLogoutHandler;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.util.Collections;

/**
 * Created by Rana on 29/12/2021.
 *
 * Camunda Web application SSO configuration for usage with KeycloakIdentityProviderPlugin.
 */
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
	/** Access to Camunda's Identity Service. */
	@Autowired
	private IdentityService identityService;

	/** Access to Spring Security OAuth2 client service. */
	@Autowired
	private OAuth2AuthorizedClientService clientService;

	private final AppProperties appProperties;

	public WebAppSecurityConfig(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		System.out.println("\n***************************");
		System.out.println("Configuring web sso ...");
		System.out.println("***************************\n");

    	http
    	.csrf().ignoringAntMatchers("/api/**", "/engine-rest/**")
    	.and()
    	.requestMatchers().antMatchers("/**").and()
        .authorizeRequests(
       		authorizeRequests ->
       		authorizeRequests
       		.antMatchers("/camunda/**", "/app/**", "/api/**", "/lib/**", "/engine-rest/**")
       		.authenticated()
       		.anyRequest()
       		.permitAll()
       		)
	    .oauth2Login()
	    .and()
	      .logout()
	      .logoutRequestMatcher(new AntPathRequestMatcher("/app/**/logout"))
	      .logoutSuccessHandler(keycloakLogoutHandler())
		.and()
		  .oauth2ResourceServer()
		  .jwt().jwkSetUri(appProperties.getJwkSetUri())
        ;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean containerBasedAuthenticationFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new ContainerBasedAuthenticationFilter());
        filterRegistration.setInitParameters(Collections.singletonMap("authentication-provider",
				KeycloakAuthenticationProvider.class.getName()));
        filterRegistration.setOrder(101); // make sure the filter is registered after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/camunda/*", "/app/*");
        return filterRegistration;
    }
 
    // The ForwardedHeaderFilter is required to correctly assemble the redirect URL for OAUth2 login. 
	// Without the filter, Spring generates an HTTP URL even though the container route is accessed through HTTPS.
    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }
	
	@Bean
	@Order(0)
	public RequestContextListener requestContextListener() {
	    return new RequestContextListener();
	}

	@Bean
	public KeycloakLogoutHandler keycloakLogoutHandler() {
    	return new KeycloakLogoutHandler(appProperties.getAuthorizationUri());
	}

    @Bean
	@DependsOn("appProperties")
    public ClientRegistrationRepository getRegistration() {
		String redirectUri = appProperties.getRedirectUri();
		if (StringUtils.isNotBlank(redirectUri)) {
			redirectUri = redirectUri.replaceAll("\"", "");
		}

		ClientRegistration registration = ClientRegistration
                .withRegistrationId("keycloak")
                .clientId(appProperties.getClientId())
                .clientSecret(appProperties.getClientSecret())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate(redirectUri)
				.scope(appProperties.getScope())
                .authorizationUri(appProperties.getAuthorizationUri())
                .userInfoUri(appProperties.getUserInfoUri())
                .jwkSetUri(appProperties.getJwkSetUri())
				.tokenUri(appProperties.getTokenUri())
                .userNameAttributeName(appProperties.getUserNameAttribute())
                .build();
        return new InMemoryClientRegistrationRepository(registration);
    }

	@Bean
	public JwtDecoder jwtDecoder() {


		NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
				JwtDecoders.fromOidcIssuerLocation(appProperties.getIssuerUri());

		OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(appProperties.getRequiredAudience());
		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(appProperties.getIssuerUri());
		OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

		jwtDecoder.setJwtValidator(withAudience);

		return jwtDecoder;
	}

	@Bean
	public FilterRegistrationBean keycloakAuthenticationFilter() {
		System.out.println("\n***************************");
		System.out.println("Configuring rest sso ...");
		System.out.println("***************************\n");

		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new KeycloakAuthenticationFilter(identityService, clientService));
		filterRegistration.setOrder(102); // make sure the filter is registered after the Spring Security Filter Chain
		filterRegistration.addUrlPatterns("/engine-rest/*");
		return filterRegistration;
	}
}