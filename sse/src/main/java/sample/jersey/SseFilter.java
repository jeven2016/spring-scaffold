package sample.jersey;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class SseFilter implements ClientRequestFilter {

  @Override
  public void filter(ClientRequestContext clientRequestContext) throws IOException {
    System.out.println("Add authentication");
    clientRequestContext.getHeaders().add("Authentication", "Bearer ssss");
  }
}
