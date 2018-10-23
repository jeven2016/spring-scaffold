package zjtech.auth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Grant type = password 密码模式（Resource Owner Password Credentials Grant）中，用户向客户端提供自己的
 * 用户名和密码。客户端使用这些信息，向"服务商提供商"索要授权。 在这种模式中，用户必须把自己的密码给客户端，但是客户端不得储存密码。这通常用在用户对客户端高度信任的情况下，
 * 比如客户端是操作系统的一部分，或者由一个著名公司出品。而认证服务器只有在其他授权模式无法执行的情况下，才能考虑使用 这种模式.
 *
 * 这种场景是：　user->(username+password)-> client -> (client+secret+username+password)-> Auth
 *                                                                                         \
 *             <------------access-token         <---------------access-token -------------
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
public class TokenServiceTest {

  @Test
  public void contextLoads() {
  }

  private String clientId = "web_app";
  private String secret = "secret";

  private String username = "reader";
  private String password = "reader";

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * With MockMvc, you're typically setting up a whole web application context and mocking the HTTP
   * requests and responses. So, although a fake DispatcherServlet is up and running, simulating how
   * your MVC stack will function, there are no real network connections made.
   *
   * With RestTemplate, you have to deploy an actual server instance to listen for the HTTP requests
   * you send.
   */
  private MockMvc mvc;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  /**
   * Create token successfully with correct credential
   *
   * @throws Exception Unkown exception
   */
  @Test
  public void create_token() throws Exception {
    String content = mvc.perform(
        post("/oauth/token?grant_type=password&username=" + username + "&password=" + password)
            .with(httpBasic(clientId, secret))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("access_token").exists())
        .andReturn()
        .getResponse().getContentAsString();
    System.out.println(content);
  }

  /**
   * Failed to create token with invalid client id and secret
   */
  @Test
  public void fail_create_token_with_invalid_client() throws Exception {
    mvc.perform(
        post("/oauth/token?grant_type=password&username=" + username + "&password=" + password)
            .with(httpBasic("invalid", "invalid"))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }


  /**
   * Failed to create token with invalid client id and secret
   */
  @Test
  public void fail_create_token_with_invalid_credential() throws Exception {
    mvc.perform(
        post("/oauth/token?grant_type=password&username=invalid&password=invalid")
            .with(httpBasic(clientId, secret))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  /**
   * Check a valid token
   */
  @Test
  public void check_valid_token() throws Exception {
    String content = mvc.perform(
        post("/oauth/token?grant_type=password&username=" + username + "&password=" + password)
            .with(httpBasic(clientId, secret))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("access_token").exists())
        .andReturn()
        .getResponse().getContentAsString();

    Map<String, String> map = objectMapper.readValue(content, Map.class);
    String accessToken = map.get("access_token");
    System.out.println("access_token=" + accessToken);

    String data = mvc
        .perform(post("/oauth/check_token?token=" + accessToken).with(httpBasic(clientId, secret)))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    System.out.println("data=" + data);
  }

  /**
   * Check a token is expired. a) create a token with a temporary client b) check the token is
   * expired c) refresh this token d) check the token is refreshed
   */
  @Test
  public void check_expired_token() throws Exception {
    //create a token
    String content = mvc.perform(
        post("/oauth/token?grant_type=password&username=" + username + "&password=" + password)
            .with(httpBasic("temp_client", "temp"))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("access_token").exists())
        .andReturn()
        .getResponse().getContentAsString();

    Map<String, String> map = objectMapper.readValue(content, Map.class);
    String accessToken = map.get("access_token");
    System.out.println("access_token=" + accessToken);

    //check the token is valid
    mvc.perform(post("/oauth/check_token?token=" + accessToken)
        .with(httpBasic(clientId, secret)))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

    //wait for 10 seconds to ensure the token is expired
    TimeUnit.SECONDS.sleep(10);

    //check token is expired and the status code is 400
    mvc.perform(post("/oauth/check_token?token=" + accessToken))
        .andExpect(status().is4xxClientError());

    //refresh the token
    String refreshTokenUri = "/oauth/refresh_token?refresh_token=" + map.get("refresh_token");
    System.out.println("refreshTokenUri=" + refreshTokenUri);
    String data = mvc.perform(
        post("/oauth/token?grant_type=refresh_token&refresh_token=" + map.get("refresh_token"))
            .with(httpBasic("temp_client", "temp")))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    System.out.println("newToken=" + data);
  }
}
