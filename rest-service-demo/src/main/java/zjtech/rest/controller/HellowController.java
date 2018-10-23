package zjtech.rest.controller;

import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/hello")
public class HellowController {

  @GetMapping
  public String sayHello() {
    return "hello" + new Random().nextInt();
  }

}
