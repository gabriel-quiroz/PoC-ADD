package com.example.primerSSO.service;

import java.util.HashSet;
import java.util.Set;

import com.azure.spring.aad.webapp.AADOAuth2UserService;
import com.azure.spring.autoconfigure.aad.AADAuthenticationProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class MyAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    Logger log = LoggerFactory.getLogger(MyAuth2UserService.class);

    AADOAuth2UserService aadoAuth2UserService;

    public MyAuth2UserService(AADAuthenticationProperties aadAuthProps) {
        aadoAuth2UserService = new AADOAuth2UserService(aadAuthProps);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("\n--->>>MyAuth2UserService--->>>loadUser()\n");

        OidcUser usuario = aadoAuth2UserService.loadUser(userRequest);
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(usuario.getAuthorities());

        // String userName = (String)
        // usuario.getIdToken().getClaims().get("unique_name");//no sirve
        String preferredUsername = (String) usuario.getIdToken().getClaims().get("preferred_username");
        String name = (String) usuario.getIdToken().getClaims().get("name");

        log.info("\n--->>>usuario: {} ", usuario);
        log.info("\n--->>>preferred_username: {}", preferredUsername);
        log.info("\n--->>>name: {}", name);
        log.info("\n--->>>mappedAuthorities: {}", mappedAuthorities);
        log.info("\n--->>>usuario.getIdToken(): {}", usuario.getIdToken());

        // README: aqui es donde puedo consultar la DB propia para obtener mas detalle
        // del usuario: ROLES, DOMAIN, OPERATION, ACTION
        // README: y meterlo en el mappedAuthorities para los roles

        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_DE_SIMS"));

        // README: puedo meter las acciones aqui
        OidcUserInfo userInfo = usuario.getUserInfo();
        log.info("\n--->>>userInfo: {}", userInfo);

        userInfo = OidcUserInfo.builder().profile("[crearSIMS, BorrarSIMS]").build();

        return new DefaultOidcUser(mappedAuthorities, usuario.getIdToken(), userInfo);
    }

}
