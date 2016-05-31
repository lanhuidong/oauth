package com.nexusy.oauth.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Collections;

/**
 * @author lan
 * @since 2016-05-31
 */
@Configuration
@EnableOAuth2Client
public class OAuth2Config {

    @Bean
    public OAuth2ProtectedResourceDetails api() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId("login");
        details.setClientId("testId");
        details.setClientSecret("testSecret");
        details.setAccessTokenUri("http://localhost:8080/server/oauth/token");
        details.setUserAuthorizationUri("http://localhost:8080/server/oauth/authorize");
        details.setPreEstablishedRedirectUri("http://localhost/oauth/callback");
        details.setScope(Collections.singletonList("LEVEL1"));
        return details;
    }

    @Bean
    public OAuth2RestTemplate loginRestTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(api(), clientContext);
    }
}
