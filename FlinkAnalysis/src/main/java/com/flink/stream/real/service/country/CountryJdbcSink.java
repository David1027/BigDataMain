package com.flink.stream.real.service.country;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.flink.stream.real.entity.country.CountryResult;
import com.flink.stream.utils.JdbcUtils;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @description: 自定义输出到mysql
 * @author: lingjian
 * @create: 2020/6/2 10:55
 */
public class CountryJdbcSink extends RichSinkFunction<CountryResult> {

  private Connection connection = null;
  private PreparedStatement insertStatement = null;
  private PreparedStatement updateStatement = null;

  @Override
  public void open(Configuration parameters) throws Exception {
    super.open(parameters);
    // 创建连接
    connection = JdbcUtils.getConnection();
    // 创建sql对象
    String insertSql = "insert into real_country (create_time,country,pv,uv) values (?,?,?,?);";
    String updateSql = "update real_country set pv = ?, uv = ? where create_time = ? and country = ?;";
    insertStatement = JdbcUtils.getStatement(connection, insertSql);
    updateStatement = JdbcUtils.getStatement(connection, updateSql);
  }

  @Override
  public void invoke(CountryResult value, Context context) throws Exception {
    updateStatement.setString(3, value.getWindowEnd());
    updateStatement.setString(4, value.getCountry());
    updateStatement.setLong(1, value.getPv());
    updateStatement.setLong(2, value.getUv());
    updateStatement.executeUpdate();
    // 如果update没有查到数据，就执行插入操作
    if (updateStatement.getUpdateCount() == 0) {
      insertStatement.setString(1, value.getWindowEnd());
      insertStatement.setString(2, value.getCountry());
      insertStatement.setLong(3, value.getPv());
      insertStatement.setLong(4, value.getUv());
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
