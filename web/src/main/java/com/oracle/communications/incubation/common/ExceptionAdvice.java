package com.oracle.communications.incubation.common;

import com.oracle.communications.incubation.common.error.BusinessError;
import com.oracle.communications.incubation.common.error.BusinessException;
import com.oracle.communications.incubation.common.error.ErrorEnum;
import com.oracle.communications.incubation.common.result.Result;
import com.oracle.communications.incubation.common.result.ResultImpl;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

  @ExceptionHandler(value = BusinessException.class)
  public ResponseEntity handleBusinessException(WebRequest req,
      BusinessException e) {
    return getResponseEntity(e.getError(), e, throwable -> {
          log.warn("a BusinessException encountered: {}", e.getError().getErrorMessage());
        }
    );
  }

  @ExceptionHandler(value = IllegalArgumentException.class)
  public ResponseEntity handleBusinessException(IllegalArgumentException e) {
    return getResponseEntity(ErrorEnum.BAD_MISSING_INPUT, e, throwable -> {
          log.warn("a IllegalArgumentException encountered: {}", e);
        }
    );
  }

  @ExceptionHandler(value = Throwable.class)
  public ResponseEntity handleBusinessException(Throwable e) {
    return getResponseEntity(ErrorEnum.INTERNAL_SERVER_ERROR, e, throwable -> {
          log.warn("a unexpected encountered: {}", e);
        }
    );
  }

  private ResponseEntity getResponseEntity(ErrorEnum errorEnum, Throwable e, Consumer logConsumer) {
    Result result = ResultImpl.builder().Ok(false).build();
    BusinessError businessError = new BusinessError(errorEnum);
    return getResponseEntity(e, logConsumer, result, businessError);
  }

  private ResponseEntity getResponseEntity(BusinessError businessError, Throwable e,
      Consumer logConsumer) {
    Result result = ResultImpl.builder().Ok(false).build();
    return getResponseEntity(e, logConsumer, result, businessError);
  }

  private ResponseEntity getResponseEntity(Throwable e, Consumer logConsumer, Result result,
      BusinessError businessError) {
    result.addGlobalError(businessError);
    int status = businessError.getErrorGroup().getCode();
    logConsumer.accept(e);
    return new ResponseEntity<>(result, HttpStatus.valueOf(status));
  }

}
