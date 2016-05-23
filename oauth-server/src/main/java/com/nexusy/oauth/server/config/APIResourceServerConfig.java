package com.nexusy.oauth.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 配置资源服务器
 *
 * @author lan
 * @since 2016-05-23
 */
@Configuration
@EnableResourceServer
public class APIResourceServerConfig extends ResourceServerConfigurerAdapter {

    public final static String API_RESOURCES_ID = "API";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(API_RESOURCES_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .requestMatchers().antMatchers("/me")
                .and()
                .authorizeRequests().antMatchers("/me").access("#oauth2.hasScope('READ')");
    }

}
