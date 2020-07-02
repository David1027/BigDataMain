package com.flink.stream.page.controller;

import java.util.Properties;

import com.flink.stream.page.entity.Page;
import com.flink.stream.page.service.PageMap;
import com.flink.stream.properties.KafkaBean;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

/**
 * @description: 测试类，实现kafka连接
 * @author: lingjian
 * @create: 2020/6/2 9:43
 */
public class PageController {

  public static void main(String[] args) throws Exception {
    // 创建flink环境
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    // 设置时间格式
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
    // 设置输入源 - kafka
    DataStreamSource<String> stream =
        env.addSource(
            new FlinkKafkaConsumer010<>("log", new SimpleStringSchema(), getProperties()));
    // 转化自定义pojo类
    DataStream<Page> dataStream = stream.map(new PageMap());

    // 输出打印
    dataStream.printToErr();

    // 执行程序
    env.execute("kafka source");
  }

  /**
   * 获取kafka的配置类
   *
   * @return Properties对象
   */
  private static Properties getProperties() {
    KafkaBean kafka = new KafkaBean();
    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", kafka.getBootstrapServers());
    properties.setProperty("key.serializer", kafka.getKeySerializer());
    properties.setProperty("value.serializer", kafka.getValueSerializer());
    properties.setProperty("group.id", "xwt_page");
    return properties;
  }
}
