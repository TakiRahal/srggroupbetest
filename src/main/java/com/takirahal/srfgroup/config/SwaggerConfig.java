package com.takirahal.srfgroup.config;

import com.takirahal.srfgroup.constants.SrfGroupConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.parameters.Parameter;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customizeOperationCustomizer() {
        return (operation, handlerMethod) -> operation
                .addParametersItem(new Parameter().in("header").required(false).description("Alert header").name("X-app-alert"))
                .addParametersItem(new Parameter().in("header").required(true).description("ar / fr / en").name(SrfGroupConstants.LANG_KEY))
                .addParametersItem(new Parameter().in("header").required(true).description("WebBrowser / MobileBrowser").name(SrfGroupConstants.SOURCE_CONNECTED_DEVICE));
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new
                Info().title("title").version("version").description("description"))
                .addSecurityItem(new SecurityRequirement().addList("my security"))
                .components(new Components().addSecuritySchemes("my security",
                        new SecurityScheme().name("my security").type(SecurityScheme.Type.HTTP).scheme("Bearer")));
    }

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI().addServersItem(new Server().url("https://myserver.com"))
//                .addServersItem(new Server().url("https://google.com"))
//                .components(new Components().addSecuritySchemes("basicScheme",
//                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
//                .info(new Info().title("SpringShop API").version("0.0.1")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
//    }
}
