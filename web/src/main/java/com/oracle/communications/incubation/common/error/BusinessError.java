/*
 * Copyright (C) 2015 Oracle and/or its affiliates. All Rights Reserved.
 * 
 * This file contains material that is confidential and proprietary to Oracle and/or its affiliates.
 * No part of this file may be reproduced or transmitted in any form or by any means, electronic or
 * mechanical, for any purpose, without the express permission of Oracle and/or its affiliates.
 */
package com.oracle.communications.incubation.common.error;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * This class represents an error encountered while processing some piece of business logic.
 */
public class BusinessError implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 568743258541729284L;
  private final ErrorEnum errorEnum;
  private final String errorMessage;
  private final String errorDetails;


  /**
   *
   * @param errorEnum
   * @param replacementVars
   */
  public BusinessError(ErrorEnum errorEnum, Object... replacementVars) {
    this(null, errorEnum, replacementVars);
  }

  /**
   *
   * @param errorDetails
   * @param errorEnum
   * @param replacementVars
   */
  public BusinessError(String errorDetails, ErrorEnum errorEnum,
      Object... replacementVars) {
    this.errorDetails = errorDetails;
    this.errorEnum = errorEnum;
    if (replacementVars == null) {
      errorMessage = errorEnum.getMessage();
    } else {
      errorMessage =
          MessageFormat.format(errorEnum.getMessage(), replacementVars);
    }
  }

  /**
   *
   * @return
   */
  public int getErrorCode() {
    return errorEnum.getCode();
  }

  /**
   *
   * @return
   */
  public ErrorGroup getErrorGroup() {
    return errorEnum.getErrorGroup();
  }

  /**
   *
   * @return
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   *
   * @return
   */
  public String getErrorDetails() {
    return errorDetails;
  }
}
