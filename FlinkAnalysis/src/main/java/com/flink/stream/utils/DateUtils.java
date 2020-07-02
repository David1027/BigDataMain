package com.flink.stream.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 时间转换工具类
 * @author: lingjian
 * @create: 2020/6/3 9:36
 */
public class DateUtils {

  /**
   * 根据 指定格式 将毫秒值转换为时间字符串
   *
   * @param millis 毫秒值
   * @param patten 指定格式 yyyy-MM-dd hh:mm:ss
   * @return String 时间字符串
   */
  public static String longToString(long millis, String patten) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millis);
    Date date = calendar.getTime();
    SimpleDateFormat format = new SimpleDateFormat(patten);
    return format.format(date);
  }
}
