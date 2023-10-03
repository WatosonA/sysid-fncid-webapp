package com.example.sysid.configration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfigration {

    @Bean
    OpenAPI myCustomOpenAPI(@Autowired(required = false) BuildProperties buildInfo) {
        return new OpenAPI()
                .info(new Info().title(buildInfo == null ? "" : buildInfo.getName())
                        .version(buildInfo == null ? "" : buildInfo.getVersion()));
    }
}
