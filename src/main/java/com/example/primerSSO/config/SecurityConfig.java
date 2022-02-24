package com.example.primerSSO.config;

//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.azure.spring.aad.webapi.AADResourceServerWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

// README: se puede utilizar para las autorizaciones por roles, pero no se pudo probar
// README: no se iba a la autorizacion de azure, quiza por la falta de roles
//// public class SecurityConfig extends ResourceServerConfigurerAdapter{
// public class SecurityConfig extends
// AADResourceServerWebSecurityConfigurerAdapter {

// @Override
// public void configure(HttpSecurity http) throws Exception {
// http.authorizeRequests()
// .antMatchers("/**")
// .permitAll()
// .anyRequest().authenticated()
// .and().formLogin()
// .and().csrf().disable()
// .httpBasic().disable();
// }
// }

// README: con este se logra que vaya a realizar el login con azure
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

    public SecurityConfig(OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService) {
        this.oidcUserService = oidcUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(oidcUserService);
    }
}
