/*
 * Copyright (C) 2015 Oracle and/or its affiliates. All Rights Reserved.
 * 
 * This file contains material that is confidential and proprietary to Oracle and/or its affiliates.
 * No part of this file may be reproduced or transmitted in any form or by any means, electronic or
 * mechanical, for any purpose, without the express permission of Oracle and/or its affiliates.
 */

package com.oracle.communications.incubation.common.error;

import com.oracle.communications.incubation.common.Utils;
import java.util.Map;

/**
 * An enumeration of "error groups" which will be translated into an appropriate status (e.g. an
 * HTTP status like 404-NotFound).
 */
public enum ErrorGroup {

  BAD_REQUEST(400, "Bad Or Missing Input"),
  UNAUTHENTICATED(401, "Unauthenticated"),
  FORBIDDEN(403, "Forbidden"),
  NOT_FOUND(404, "Object Not Found"),
  CONFLICT(409, "Conflict Found"),
  UNPROCESSABLE_INPUT(422, "Unprocessable Input"),
  INTERNAL_ERROR(500, "Internal Server Error"),
  DUMMY(0, "Dummy Error");

  private final int code;
  private final String message;
  /**
   * A map from status code to BusinessErrorGroup Enum.
   */
  private final static Map<Integer, ErrorGroup> lookupMap =
      Utils.buildLookupMap(values(), ErrorGroup::getCode);

  private ErrorGroup(int code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   *
   * @return
   */
  public int getCode() {
    return code;
  }

  /**
   *
   * @return
   */
  public String getMessage() {
    return message;
  }

  /**
   * Translate from status code to BusinessErrorGroup Enum.
   */
  public static ErrorGroup translate(Integer value) {
    ErrorGroup retVal = lookupMap.get(value);
    if (retVal == null) {
      return DUMMY;
    }
    return retVal;
  }
}
