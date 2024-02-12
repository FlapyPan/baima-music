package com.baima.music.config;


import com.baima.music.utils.Constants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;

@Configuration
public class Swagger3Config {

    /**
     * 给接口添加额外简介
     */
    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            var preAuthorizeAnnotation = handlerMethod.getMethodAnnotation(PreAuthorize.class);
            if (preAuthorizeAnnotation != null) {
                var sb = new StringBuilder();
                var summary = operation.getSummary();
                if (summary != null) sb.append(summary);
                sb.append(" | 需要 ")
                        .append(preAuthorizeAnnotation.value().replaceAll("hasRole|\\(|\\)|\\'", ""))
                        .append(" 权限");
                operation.setSummary(sb.toString());
            }

            return operation;
        };
    }

    @Bean
    public OpenAPI openAPI() {
        // swagger3的配置
        return new OpenAPI()
                .info(new Info().title(Constants.PROJECT_NAME)
                        .description(Constants.SWAGGER_INFO_DESCRIPTION)
                        .version(Constants.SWAGGER_INFO_VERSION)
                        .license(new License().name(Constants.SWAGGER_INFO_LICENSE_NAME).url(Constants.SWAGGER_INFO_LICENSE_URL)))
                .schemaRequirement(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .name(HttpHeaders.AUTHORIZATION)
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")
                        .scheme("Bearer"))
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
    }

}