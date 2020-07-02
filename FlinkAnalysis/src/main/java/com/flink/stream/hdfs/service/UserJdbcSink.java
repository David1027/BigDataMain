package com.flink.stream.hdfs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.flink.stream.hdfs.entity.UserLogCount;
import com.flink.stream.utils.DateUtils;
import com.flink.stream.utils.JdbcUtils;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @description: 自定义输出到mysql
 * @author: lingjian
 * @create: 2020/6/2 10:55
 */
public class UserJdbcSink extends RichSinkFunction<UserLogCount> {

  private Connection connection = null;
  private PreparedStatement insertStatement = null;
  private PreparedStatement updateStatement = null;

  @Override
  public void open(Configuration parameters) throws Exception {
    super.open(parameters);
    // 创建连接
    connection = JdbcUtils.getConnection();
    // 创建sql对象
    String insertSql = "insert into net_count (create_time,url,count) values (?,?,?);";
    String updateSql = "update net_count set count = ? where create_time = ? and url = ?;";
    insertStatement = JdbcUtils.getStatement(connection, insertSql);
    updateStatement = JdbcUtils.getStatement(connection, updateSql);
  }

  @Override
  public void invoke(UserLogCount value, Context context) throws Exception {
    String createTime = DateUtils.longToString(value.getWindowEnd(), "yyyy-MM-dd");
    updateStatement.setLong(1, value.getCount());
    updateStatement.setString(2, createTime);
    updateStatement.setString(3, value.getUrl());
    updateStatement.executeUpdate();
    // 如果update没有查到数据，就执行插入操作
    if (updateStatement.getUpdateCount() == 0) {
      insertStatement.setString(1, createTime);
      insertStatement.setString(2, value.getUrl());
      insertStatement.setLong(3, value.getCount());
      insertStatement.executeUpdate();
    }
  }

  @Override
  public void close() throws Exception {
    super.close();
    JdbcUtils.closeStatement(insertStatement, updateStatement);
    JdbcUtils.closeConnection(connection);
  }
}
