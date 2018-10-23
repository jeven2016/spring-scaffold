package sample.jersey;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.springframework.web.bind.annotation.GetMapping;

@Path("/sse")
public class SseController {

  @GET
  @Path("/mobile/greeting")
  public String forGet() {
    return "hello2";
  }

  @POST
  @Path("{type}/{value}")
  public String forPost(
      @PathParam("type") String type,
      @PathParam("value") String value) {
    return type + "_" + value;
  }


  @GET
  @Produces(SseFeature.SERVER_SENT_EVENTS)
  public EventOutput getServerSentEvents(@Context HttpServletRequest request) {
    System.out.println(request.getHeader("Authentication"));
    System.out.println("a request is received......");
    final EventOutput eventOutput = new EventOutput();
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          for (int i = 0; i < 10; i++) {
            // ... code that waits 1 second
            final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
            eventBuilder.name("message-to-client");
            eventBuilder.data(String.class, "Hello world " + i + "!");
            final OutboundEvent event = eventBuilder.build();
            eventOutput.write(event);
          }
        } catch (IOException e) {
          throw new RuntimeException("Error when writing the event.", e);
        } finally {
          try {
            eventOutput.close();
          } catch (IOException ioClose) {
            throw new RuntimeException("Error when closing the event output.", ioClose);
          }
        }
      }
    }).start();
    return eventOutput;
  }

}
