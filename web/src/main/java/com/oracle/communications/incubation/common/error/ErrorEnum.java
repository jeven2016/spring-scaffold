/*
 * Copyright (C) 2015 Oracle and/or its affiliates.  All Rights Reserved.
 *
 * This file contains material that is confidential and proprietary
 * to Oracle and/or its affiliates.  No part of this file may be reproduced
 * or transmitted in any form or by any means, electronic or
 * mechanical, for any purpose, without the express permission of
 * Oracle and/or its affiliates.
 */
package com.oracle.communications.incubation.common.error;

/**
 * An enumeration of all possible business errors that can occur in the system. Each enumeration
 * includes the following information: <br> 1) A unique application error code which the user can
 * look up in the docs to find out more about the specific error condition. <br> 2) An "error group"
 * value which will be translated into an appropriate status (e.g. an HTTP status like
 * 404-NotFound). <br> 3) A textual message providing a brief explanation of the error condition.
 */
public enum ErrorEnum {

  //Generic errors
  BAD_MISSING_INPUT(4000, ErrorGroup.BAD_REQUEST, "Bad or missing input"),
  NOT_FOUND(4001, ErrorGroup.NOT_FOUND, "The specified {0} was not found: {1}"),
  ENUM_NOT_FOUND(4002, ErrorGroup.NOT_FOUND,
      "The specified {0} enumeration value was not found in {1}"),
  // Unprocessable-input (validation) errors
  NOT_SUPPORTED(4100, ErrorGroup.UNPROCESSABLE_INPUT, "Operation not supported"),
  ALREADY_EXISTS(4101, ErrorGroup.UNPROCESSABLE_INPUT, "The specified {0} already exists: {1}"),
  ALREADY_EXISTS_COMPOUND(4102, ErrorGroup.UNPROCESSABLE_INPUT,
      "The specified compound key <{0}, {1}> already exists: <{2}, {3}>"),
  VALIDATION_ERROR_GENERIC(4103, ErrorGroup.UNPROCESSABLE_INPUT, "Validation error"),
  VALIDATION_ERROR_VALUES(4104, ErrorGroup.UNPROCESSABLE_INPUT, "Invalid value for {0}: {1}"),
  VALIDATION_ERROR_MESSAGE(4105, ErrorGroup.UNPROCESSABLE_INPUT, "Validation error: {0}"),
  UNIQUENESS_CHECK_FAILED(4108, ErrorGroup.UNPROCESSABLE_INPUT,
      "Duplicate {1}: {2}. {1} must be unique."),
  UNIQUENESS_CHECK_FAILED_FOR_CHILD(4110, ErrorGroup.UNPROCESSABLE_INPUT,
      "Duplicate {1}: {2}. {1} must be unique per {3}."),
  REQUIRED_FIELD_VALIDATION(4111, ErrorGroup.UNPROCESSABLE_INPUT,
      "{1} is required. {1} cannot be empty."),
  REQUIRED_FIELD_VALIDATION_FOR_CHILD(4112, ErrorGroup.UNPROCESSABLE_INPUT,
      "{1} is required for {2}. {1} cannot be empty."),
  //Cannot Delete an entity that is used in Policy
  CAN_NOT_DELETE_ENTITY(4113, ErrorGroup.UNPROCESSABLE_INPUT,
      "Cannot delete {0} because it is used in policies - {1}"),
  // Invalid name validation errors
  INVALID_NAME_SINGLE_PERIOD(4114, ErrorGroup.UNPROCESSABLE_INPUT,
      "Single period (.) cannot be used as a name."),
  INVALID_NAME_DOUBLE_PERIOD(4115, ErrorGroup.UNPROCESSABLE_INPUT,
      "Double-period (..) cannot be used as a name."),
  // Enumeration for One-to-Many peer relationships
  ALREADY_ASSOCIATED_ENTITY(4114, ErrorGroup.UNPROCESSABLE_INPUT,
      "Cannot associate {0} because it is already associated to another {1}"),
  INVALID_CMTS_PORT(4205, ErrorGroup.UNPROCESSABLE_INPUT,
      "Invalid {1}. {1} cannot be 0 when {2} is set to true"),
  // Security-related errors
  UNAUTHENTICATED(4209, ErrorGroup.UNAUTHENTICATED, "Unauthenticated"),
  FORBIDDEN(4210, ErrorGroup.FORBIDDEN, "Forbidden"),
  // Conflicts
  CONFLICT_FOUND(4300, ErrorGroup.CONFLICT, "Conflict Found: {0}"),
  // Catch-all server error
  INTERNAL_SERVER_ERROR(5000, ErrorGroup.INTERNAL_ERROR, "Internal Server Error"),
  DUMMY(0, ErrorGroup.INTERNAL_ERROR, "Dummy Error"),

  CUSTOMER_NOT_FOUND(5001, ErrorGroup.UNAUTHENTICATED, "The customer {0} does not exist."),
  DUPLICATED_CUSTOMER(5002, ErrorGroup.CONFLICT, "Duplicate customer.");

  private final int code;
  private final ErrorGroup errorGroup;
  private final String message;

  private ErrorEnum(int code, ErrorGroup errorGroup,
      String message) {
    this.code = code;
    this.errorGroup = errorGroup;
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
  public ErrorGroup getErrorGroup() {
    return errorGroup;
  }

  /**
   *
   * @return
   */
  public String getMessage() {
    return message;
  }
}
