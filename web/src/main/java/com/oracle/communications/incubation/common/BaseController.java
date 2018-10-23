package com.oracle.communications.incubation.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.communications.incubation.common.error.BusinessError;
import com.oracle.communications.incubation.common.error.BusinessException;
import com.oracle.communications.incubation.common.error.ErrorEnum;
import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class BaseController {

  @Autowired
  private ObjectMapper objMapper;

  /**
   * Deserialize an object to string
   */
  protected <T> String deserialize(T value) throws JsonProcessingException {
    return objMapper.writeValueAsString(value);
  }

  /**
   * Serialize the string to a object
   *
   * @return T the object your needed
   */
  protected <T> T serialize(String value, Class<T> cls) throws IOException {
    return objMapper.readValue(value, cls);
  }

  protected void notNull(String... values) {
    boolean isEmpty = Stream.of(values).anyMatch(StringUtils::isEmpty);
    if (isEmpty) {
      throw new BusinessException(new BusinessError(ErrorEnum.BAD_MISSING_INPUT));
    }
  }
}
