/**
 * Oracle Corporation, Inc.
 *
 * Copyright (C) 2016, Oracle and/or its affiliates. All rights reserved
 */

package com.oracle.communications.incubation.common.result;

import com.oracle.communications.incubation.common.error.BusinessError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ResultImpl<E> implements Result<E> {

  @Builder.Default
  @Getter
  @Setter
  private boolean Ok = true;

  @Getter
  @Setter
  private E data;

  @Getter
  @Builder.Default
  private List<BusinessError> globalError = new ArrayList<>();

  @Getter
  @Builder.Default
  private Map<String, List<BusinessError>> fieldError = new HashMap<>();

  @Override
  public boolean hasGlobalError() {
    return !globalError.isEmpty();
  }

  @Override
  public boolean hasFieldError() {
    return !fieldError.isEmpty();
  }

  @Override
  public void addGlobalError(BusinessError e) {
    globalError.add(e);
  }

  @Override
  public void addFieldError(String field, BusinessError e) {
    List<BusinessError> errors;
    if (fieldError.containsKey(field)) {
      errors = fieldError.get(field);
      errors.add(e);
    } else {
      errors = new ArrayList<>();
      errors.add(e);
      fieldError.put(field, errors);
    }
  }

}
