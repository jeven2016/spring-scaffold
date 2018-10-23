package com.oracle.communications.incubation;

import com.oracle.communications.incubation.init.ApplicationEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
@Slf4j
public class WebApplication implements CommandLineRunner {

  //for basic authentication
/*  @Bean
  RestOperations rest(RestTemplateBuilder restTemplateBuilder,
      @Value("${ohs.username}") String username,
      @Value("${ohs.password}") String password) {
    return restTemplateBuilder.basicAuthorization(username, password).build();
  }*/

/*
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }*/

  public static void main(String[] args) {
//    SpringApplication.run(Application.class, args);
    //or
    SpringApplication app = new SpringApplication(WebApplication.class);
//    app.setBannerMode(Banner.Mode.OFF);
    app.addListeners(new ApplicationEventHandler());
    app.run(args);
  }


  /**
   * Interface used to indicate that a bean should run when it is contained within a
   * SpringApplication. Multiple CommandLineRunner beans can be defined within the same application
   * context and can be ordered using the Ordered interface or @Order annotation. If you need access
   * to ApplicationArguments instead of the raw String array consider using ApplicationRunner.
   */
  @Override
  public void run(String... args) throws Exception {

  }
}
