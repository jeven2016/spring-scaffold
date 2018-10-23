package zjtech.auth.ctrlr;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWordController {

  @GetMapping
//  @Secured("hasRole('USER')")
  public String greeting() {
    return "greeting";
  }
}
