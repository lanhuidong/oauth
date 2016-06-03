package com.nexusy.oauth.client.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lan
 * @since 2016-05-31
 */
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        setUserDetailsService(userDetailsService);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(MyAuthenticationToken.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2AccessToken accessToken = restTemplate.getAccessToken();
        System.out.println("access token:" + accessToken.getValue());
        User user = restTemplate.getForObject("http://localhost:8080/server/me", User.class);
        return new MyAuthenticationToken(user.getAuthorities(), null, user);
    }
}
