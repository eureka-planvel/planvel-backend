package com.mycom.myapp.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
  private boolean success;
  private String msg;
  private T data;

  public static <T> CommonResponse<T> success(T data, String msg) {
    return new CommonResponse<>(true, msg, data);
  }

  public static <T> CommonResponse<T> success(String msg) {
    return new CommonResponse<>(true, msg, null);
  }

  public static <T> CommonResponse<T> fail(String msg) {
    return new CommonResponse<>(false, msg, null);
  }
}
