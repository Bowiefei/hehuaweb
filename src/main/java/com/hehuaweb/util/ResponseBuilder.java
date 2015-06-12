package com.hehuaweb.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author mixueqiang
 * @since Mar 10, 2014
 */
public final class ResponseBuilder {
  public static final String OK;
  public static final String FAIL;
  public static final String ERR_NEED_LOGIN;
  public static final String ERR_PERMISSION_DENIED;

  static {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("ec", 0);
    OK = new Gson().toJson(result);

    result = new HashMap<String, Object>();
    result.put("ec", -1);
    FAIL = new Gson().toJson(result);

    result = new HashMap<String, Object>();
    result.put("ec", 403);
    result.put("em", "You need to signin before this operation!");
    ERR_NEED_LOGIN = new Gson().toJson(result);

    result = new HashMap<String, Object>();
    result.put("ec", 405);
    result.put("em", "Permission denied!");
    ERR_PERMISSION_DENIED = new Gson().toJson(result);

  }

  public static final String ok(String message) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("ec", 0);
    result.put("em", message);
    return new Gson().toJson(result);
  }

  public static final String ok(long id) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("ec", 0);
    result.put("id", id);
    return new Gson().toJson(result);
  }

  public static final String okWithResult(Object obj) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("ec", 0);
    result.put("rst", obj);
    return new Gson().toJson(result);
  }

  public static final String error(int errorCode, String errorMsg) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("ec", errorCode);
    result.put("em", errorMsg);
    return new Gson().toJson(result);
  }

  public static final String error(Throwable throwable) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("ec", 500);
    result.put("em", throwable.getMessage());
    return new Gson().toJson(result);
  }

  public static final String errorWithResult(int errorCode, Object obj) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("ec", errorCode);
    result.put("rst", obj);
    return new Gson().toJson(result);
  }

  private ResponseBuilder() {
  }
}
