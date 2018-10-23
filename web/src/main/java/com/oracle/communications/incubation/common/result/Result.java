/**
 * Oracle Corporation, Inc.
 *
 * Copyright (C) 2016, Oracle and/or its affiliates. All rights reserved
 */

package com.oracle.communications.incubation.common.result;

import com.oracle.communications.incubation.common.error.BusinessError;
import java.util.List;
import java.util.Map;

public interface Result<E> {

  boolean isOk();

  void setOk(boolean ok);

  E getData();

  void setData(E data);

  boolean hasGlobalError();

  List<BusinessError> getGlobalError();

  void addGlobalError(BusinessError e);

  boolean hasFieldError();

  Map<String, List<BusinessError>> getFieldError();

  void addFieldError(String field, BusinessError e);

}
