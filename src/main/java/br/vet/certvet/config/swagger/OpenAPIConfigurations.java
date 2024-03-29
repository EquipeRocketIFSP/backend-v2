package br.vet.certvet.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfigurations {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("certVetApp")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI certVetAPI(){
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
//                .addSecurityItem(new SecurityRequirement()
//                        .addList(securitySchemeName)
//                )
                .info(new Info()
                        .title("CertVetApp")
                        .description("Documentação da aplicação CertVet")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .in(SecurityScheme.In.HEADER)
                                                .bearerFormat("JWT"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("CertVet")
                        .url("https://github.com/EquipeRocketIFSP")
                );
    }
}
