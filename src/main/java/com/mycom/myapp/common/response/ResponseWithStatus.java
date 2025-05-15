package com.mycom.myapp.common.response;

import org.springframework.http.HttpStatus;

public class ResponseWithStatus<T> {
  private final HttpStatus status;
  private final CommonResponse<T> body;

  public ResponseWithStatus(HttpStatus status, CommonResponse<T> body) {
    this.status = status;
    this.body = body;
  }

  public static <T> ResponseWithStatus<T> ok(CommonResponse<T> body) {
    return new ResponseWithStatus<>(HttpStatus.OK, body);
  }

  public static <T> ResponseWithStatus<T> conflict(CommonResponse<T> body) {
    return new ResponseWithStatus<>(HttpStatus.CONFLICT, body);
  }

  public static <T> ResponseWithStatus<T> badRequest(CommonResponse<T> body) {
    return new ResponseWithStatus<>(HttpStatus.BAD_REQUEST, body);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public CommonResponse<T> getBody() {
    return body;
  }
}