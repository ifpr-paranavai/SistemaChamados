package com.api.sistemachamados;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Aplicação Sistemas Chamados", version = "1.0", description = "Aplicação para controle de Ordens de Serviço"))
@SecurityScheme(name = "sistemachamadosapi", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class ApiSistemaChamadosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSistemaChamadosApplication.class, args);
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
            = new ReloadableResourceBundleMessageSource();

        messageSource.setBasenames("classpath:/messages/api_error_messages",
            "classpath:/messages/api_response_messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
