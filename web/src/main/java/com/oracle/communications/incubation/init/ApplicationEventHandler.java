package com.oracle.communications.incubation.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Some events are actually triggered before the ApplicationContext is created, so you cannot
 * register a listener on those as a @Bean. You can register them with the
 * SpringApplication.addListeners(…​) or SpringApplicationBuilder.listeners(…​) methods. If you want
 * those listeners to be registered automatically, regardless of the way the application is created,
 * you can add a META-INF/spring.factories file to your project and reference your listener(s) by
 * using the org.springframework.context.ApplicationListener key, as shown in the following example:
 * org.springframework.context.ApplicationListener=com.example.project.MyListener
 */
@Slf4j
public class ApplicationEventHandler implements ApplicationListener {

  /**
   * Application events are sent in the following order, as your application runs:
   *
   * An ApplicationStartingEvent is sent at the start of a run but before any processing except the
   * registration of listeners and initializers.
   *
   * An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the context
   * is known but before the context is created.
   *
   * An ApplicationPreparedEvent is sent just before the refresh is started but after bean
   * definitions have been loaded.
   *
   * An ApplicationReadyEvent is sent after the refresh and any related callbacks have been
   * processed, to indicate that the application is ready to service requests.
   *
   * An ApplicationFailedEvent is sent if there is an exception on startup.
   */
  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ApplicationStartingEvent) {
      log.info("ApplicationStartingEvent is received.");
    }
    if (event instanceof ApplicationEnvironmentPreparedEvent) {
      log.info("ApplicationEnvironmentPreparedEvent is received.");
    }
    if (event instanceof ApplicationPreparedEvent) {
      log.info("ApplicationPreparedEvent is received.");
    }
    if (event instanceof ApplicationReadyEvent) {
      log.info("ApplicationReadyEvent is received.");
    }
    if (event instanceof ApplicationFailedEvent) {
      log.info("ApplicationFailedEvent is received.");
    }
  }
}
