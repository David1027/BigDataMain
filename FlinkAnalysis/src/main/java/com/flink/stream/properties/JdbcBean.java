package com.flink.stream.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Data;

/**
 * @description: jdbc的配置类
 * @author: lingjian
 * @create: 2020/6/5 9:27
 */
@Data
public class JdbcBean {
  /** 数据库驱动 */
  private String driver;

  /** 数据库连接url */
  private String url;

  /** 数据库用户名 */
  private String username;

  /** 数据库密码 */
  private String password;

  public JdbcBean() {
    InputStream inputStream = getClass().getResourceAsStream("/my.properties");
    Properties prop = new Properties();
    try {
      prop.load(inputStream);
      this.driver = prop.getProperty("jdbc.driver");
      this.url = prop.getProperty("jdbc.url");
      this.username = prop.getProperty("jdbc.username");
      this.password = prop.getProperty("jdbc.password");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
