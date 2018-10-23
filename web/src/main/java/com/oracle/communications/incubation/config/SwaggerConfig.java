package com.oracle.communications.incubation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
class SwaggerConfig extends WebMvcConfigurerAdapter {

  /**
   * Configure for swagger2
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  /**
   * Docket: Springfox’s, primary api configuration mechanism is initialized for swagger
   * specification 2.0
   *
   * A builder which is intended to be the primary interface into the swagger-springmvc framework.
   * Provides sensible defaults and convenience methods for configuration.
   */
  @Bean
  Docket myApi() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.oracle.communications.incubation"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))

        //PathSelectors provides additional filtering with predicates
        // which scan the request paths of your application. You can use any(), none(), regex(), or ant().
        .paths(PathSelectors.any())
        .build();

  }

  ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Web Manager API Documentation")
        .description("Web Manager API Documentation")
        .version("0.1")
        .build();
  }
}
