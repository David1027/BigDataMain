package com.flink.stream.utils.kafka;

import java.util.Properties;

import com.flink.stream.properties.KafkaBean;

/**
 * @description: kafka工具类
 * @author: lingjian
 * @create: 2020/6/23 9:08
 */
public class KafkaUtils {

  /**
   * 获取kafka的配置类
   *
   * @return Properties对象
   */
  public static Properties getProperties() {
    KafkaBean kafka = new KafkaBean();
    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", kafka.getBootstrapServers());
    properties.setProperty("key.serializer", kafka.getKeySerializer());
    properties.setProperty("value.serializer", kafka.getValueSerializer());
    properties.setProperty("group.id", kafka.getGroupId());
    return properties;
  }
}
