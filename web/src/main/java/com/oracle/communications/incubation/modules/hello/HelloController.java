package com.oracle.communications.incubation.modules.hello;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "Customer Subscription Manager", tags = "Customer Subscription Manager")
@RestController
@RequestMapping("/hello")
public class HelloController {

  @ApiOperation(value = "Purchase C2G SBC Solution")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad or missing input")
  })
  @GetMapping
  public ResponseEntity say() {
    return new ResponseEntity("hello~~~", HttpStatus.OK);
  }
}
