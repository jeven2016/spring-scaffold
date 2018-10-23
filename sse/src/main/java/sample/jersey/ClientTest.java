package sample.jersey;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

public class ClientTest {

  static Pattern pattern = Pattern.compile(".*?@(.*?)\\..*");

  public static void main(String[] args) throws URISyntaxException {
    listen();
  }

  private static void listen() {
    Client client = ClientBuilder.newBuilder()
        .register(SseFeature.class).build();
    client.register(new SseFilter());

    WebTarget target = client.target("http://127.0.0.1:8080/sse");
    EventSource eventSource = EventSource.target(target).build();
    EventListener listener = new EventListener() {
      @Override
      public void onEvent(InboundEvent inboundEvent) {
        System.out.println(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
      }
    };
    eventSource.register(listener, "message-to-client");
    eventSource.open();

    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if (eventSource.isOpen()) {
        eventSource.close();
      }
    }
//    eventSource.close();
  }

}
