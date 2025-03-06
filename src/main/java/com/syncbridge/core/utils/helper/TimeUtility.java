package com.syncbridge.core.utils.helper;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeUtility {
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public String getCurrentTime() {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    return sdf.format(new Date());
  }
}
