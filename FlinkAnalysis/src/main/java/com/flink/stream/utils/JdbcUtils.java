package com.flink.stream.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.flink.stream.properties.JdbcBean;

/**
 * @description: jdbc工具类
 * @author: lingjian
 * @create: 2020/6/3 11:28
 */
public class JdbcUtils {

  private static String driver;
  private static String url;
  private static String username;
  private static String password;

  // 注册JDBC驱动
  static {
    try {
      JdbcBean jdbc = new JdbcBean();
      driver = jdbc.getDriver();
      url = jdbc.getUrl();
      username = jdbc.getUsername();
      password = jdbc.getPassword();
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 创建连接
   *
   * @return Connection 连接对象
   */
  public static Connection getConnection() {
    // 创建连接
    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取sql运行对象
   *
   * @param connection 数据库连接
   * @param sql sql语句
   * @return PreparedStatement sql运行对象
   */
  public static PreparedStatement getStatement(Connection connection, String sql) {
    try {
      return connection.prepareStatement(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 关闭数据库连接
   *
   * @param connection 连接对象
   */
  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 关闭sql运行对象
   *
   * @param statement sql运行对象
   */
  public static void closeStatement(PreparedStatement statement) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 关闭sql运行对象-重载
   *
   * @param statement1 sql运行对象
   * @param statement2 sql运行对象
   */
  public static void closeStatement(PreparedStatement statement1, PreparedStatement statement2) {
    if (statement1 != null) {
      try {
        statement1.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (statement2 != null) {
      try {
        statement2.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
