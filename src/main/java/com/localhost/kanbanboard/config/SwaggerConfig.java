package com.localhost.kanbanboard.config;

import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.AuthorizationScope;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.spi.DocumentationType;
import org.springframework.context.annotation.Bean;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${jwt.clientId}")
    private String clientId;

    @Value("${jwt.secret}")
    private String clientSecret;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.localhost.kanbanboard.controller"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(Lists.newArrayList(securityScheme()))
            .securityContexts(Lists.newArrayList(securityContext()))
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false);
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .scopeSeparator(" ")
            .build();
    }

    private AuthorizationScope[] scopes() {
	    AuthorizationScope[] scopes = {
            new AuthorizationScope("read", "for read operations"),
            new AuthorizationScope("write", "for write operations")
        };
	    return scopes;
    }

    private List<SecurityReference> securityReference() {
        return Lists.newArrayList(new SecurityReference("spring_oauth", scopes()));
    }

    public SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("http://localhost:8080/oauth/token");

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
            .grantTypes(Lists.newArrayList(grantType))
            .scopes(Lists.newArrayList(scopes()))
            .build();
        
        return oauth;
    }

    private SecurityContext securityContext() {
	    return SecurityContext.builder()
          .securityReferences(securityReference())
          .forPaths(Predicates.alwaysTrue())
	      .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "Kanban Board API",
            "Api to build an trello like platform",
            "V1.2.4",
            "",
            new Contact("Vinícius", "https://github.com/hvini", "vhpcavalcanti@outlook.com"),
            "License",
            "https://github.com/hvini/kanban-board",
            Collections.emptyList()
        );
    }
}