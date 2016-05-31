package com.nexusy.oauth.client.config;

import com.nexusy.oauth.client.auth.MyAuthenticationFilter;
import com.nexusy.oauth.client.auth.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author lan
 * @since 2016-05-23
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.nexusy.oauth.client.auth"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationProvider provider;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
        auth.inMemoryAuthentication().withUser("user").password("111111").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(oAuth2ClientContextFilter(), ExceptionTranslationFilter.class)
                .addFilterAfter(myAuthenticationFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests().antMatchers("/").permitAll().anyRequest().hasRole("USER")
                .and()
                .exceptionHandling().accessDeniedPage("/?authorization_error=true")
                .and()
                .csrf()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                .formLogin().loginPage("/");
    }

    @Bean
    public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
        return new OAuth2ClientContextFilter();
    }

    @Bean
    public MyAuthenticationFilter myAuthenticationFilter() {
        MyAuthenticationFilter filter = new MyAuthenticationFilter();
        try {
            filter.setAuthenticationManager(authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setFilterProcessesUrl("/oauth/callback");
        filter.setAuthenticationSuccessUrl("/u/index");
        filter.setAuthenticationFailureUrl("/");
        return filter;
    }

}
