package com.oracle.communications.incubation.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
public class WebConfig {

  /**
   * Enable CORS support
   *
   * @return WebMvcConfigurer
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowedOrigins("*");
      }
    };
  }

  /**
   * Enable another http connector for HTTP on port 8080 Spring Boot doesn’t support the
   * configuration of both an HTTP connector and an HTTPS connector via application.properties. If
   * you want to have both then you’ll need to configure one of them programmatically. It’s
   * recommended to use application.properties to configure HTTPS as the HTTP connector is the
   * easier of the two to configure programmatically.
   *@param httpPort the http port
   * @return EmbeddedServletContainerFactory
   */
  @Bean
  public EmbeddedServletContainerFactory servletContainer(@Value("${http.port}") int httpPort) {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setPort(httpPort);
    connector.setSecure(false);

    //create another connector for HTTP
    tomcat.addAdditionalTomcatConnectors(connector);
    return tomcat;
  }
}
