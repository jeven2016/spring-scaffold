/*
 * Copyright (C) 2016 Oracle and/or its affiliates. All Rights Reserved.
 * 
 * This file contains material that is confidential and proprietary to Oracle and/or its affiliates.
 * No part of this file may be reproduced or transmitted in any form or by any means, electronic or
 * mechanical, for any purpose, without the express permission of Oracle and/or its affiliates.
 */
package com.oracle.communications.incubation.common.error;


/**
 * A custom exception class that wraps a BusinessError representing an error encountered while
 * processing some piece of business logic.
 *
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final BusinessError error;

  /**
   *
   * @param error
   */
  public BusinessException(BusinessError error) {
    // Automatically use the errorMessage for the exception's message.
    super(error.getErrorMessage());
    this.error = error;
  }

  /**
   *
   * @param message
   * @param cause
   * @param error
   */
  public BusinessException(String message, Throwable cause, BusinessError error) {
    super(message, cause);
    this.error = error;
  }

  /**
   *
   * @param message
   * @param error
   */
  public BusinessException(String message, BusinessError error) {
    super(message);
    this.error = error;
  }

  /**
   *
   * @param cause
   * @param error
   */
  public BusinessException(Throwable cause, BusinessError error) {
    super(cause);
    this.error = error;
  }

  /**
   *
   * @return
   */
  public BusinessError getError() {
    return this.error;
  }
}
