package com.flink.stream.utils.source;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @description: 流量类型工具类
 * @author: lingjian
 * @create: 2020/6/28 9:14
 */
public class SourceTypeUtils {

  private static Map<String, String> map = new HashMap<>();

  static {
    try {
      Properties properties = PropertiesLoaderUtils.loadAllProperties("ref.properties");
      for (String key : properties.stringPropertyNames()) {
        String value = properties.getProperty(key);
        map.put(key, value);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getSourceType(String ref) {
    if (StringUtils.isBlank(ref)) {
      return "interview";
    }
    for (String key : map.keySet()) {
      if (ref.contains(map.get(key))) {
        return key;
      }
    }
    return "other";
  }

  public static void main(String[] args) {
    String type = getSourceType("http://localhost/demo/a");
    System.err.println(type);
  }
}
