package com.example.primerSSO.config;

// import java.util.HashSet;
// import java.util.Set;

// import com.azure.spring.aad.webapi.AADResourceServerWebSecurityConfigurerAdapter;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
// import org.springframework.security.oauth2.core.OAuth2AccessToken;
// import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.example.primerSSO.service.MyAuth2UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    // private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

    // public SecurityConfig(OAuth2UserService<OidcUserRequest, OidcUser>
    // oidcUserService) {
    // this.oidcUserService = oidcUserService;
    // }
    // ---------------------------------------------------------------- A
    // private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
    // log.info("entro chinga");
    // final OidcUserService delegate = new OidcUserService();

    // return (userRequest) -> {
    // // Delegate to the default implementation for loading a user
    // OidcUser oidcUser = delegate.loadUser(userRequest);

    // OAuth2AccessToken accessToken = userRequest.getAccessToken();
    // Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

    // log.info("--->>>getAttributes: {}",oidcUser.getAttributes());
    // log.info("--->>>grant_type: {}", oidcUser.getAuthorities());
    // log.info("--->>>name: {}", oidcUser.getName());

    // // TODO
    // // 1) Fetch the authority information from the protected resource using
    // accessToken
    // // 2) Map the authority information to one or more GrantedAuthority's and add
    // it to mappedAuthorities

    // // 3) Create a copy of oidcUser but use the mappedAuthorities instead
    // oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(),
    // oidcUser.getUserInfo());

    // return oidcUser;
    // };
    // }

    // ---------------------------------------------------------------- B
    @Autowired
    private AADAuthenticationProperties aadAuthProps;
    // @Autowired
    // private ServiceEndpointsProperties serviceEndpointsProps;

    @Bean
    protected OAuth2UserService<OidcUserRequest, OidcUser> myAuth2UserService() {
        return new MyAuth2UserService(aadAuthProps);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                // .oidcUserService(oidcUserService);
                // .oidcUserService(this.oidcUserService());
                .oidcUserService(myAuth2UserService());
    }
}
