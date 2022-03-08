package com.example.primerSSO.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// README: no se puede PreAuthorize porque no se pudieron crear roles en azure gratuito

@RestController
public class HelloController {

    Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("admin")
    @ResponseBody
    // @PreAuthorize("hasAuthority('APPROLE_Admin')")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String admin() {
        return "endpoint protegido por el rol del usuario";
    }

    @GetMapping("otro")
    @ResponseBody
    public String otro(OAuth2AuthenticationToken token) {

        log.info("\n--->>>HelloController--->>>otro()\n");

        final OAuth2User user = token.getPrincipal();

        log.info("--->>>getAttributes: {}",user.getAttributes());
        log.info("--->>>getAuthorities: {}", user.getAuthorities());
        log.info("--->>>name: {}", user.getName());

        return "endpoint sin @PreAuthorize";
    }

    @GetMapping("logs")
    @ResponseBody
    public String logs() {

        // log.trace("A TRACE Message");
        // log.debug("A DEBUG Message");
        // log.info("An INFO Message");
        // log.warn("A WARN Message");
        // log.error("An ERROR Message");

        return "enpoint para forzar el login";
    }

}