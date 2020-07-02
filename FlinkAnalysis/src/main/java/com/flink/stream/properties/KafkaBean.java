package com.flink.stream.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Data;

/**
 * @description: kafka的配置类
 * @author: lingjian
 * @create: 2020/6/5 9:39
 */
@Data
public class KafkaBean {

  /** kafka的服务器地址 */
  private String bootstrapServers;

  /** kafka的key序列化工具类 */
  private String keySerializer;

  /** kafka的value序列化工具类 */
  private String valueSerializer;

  /** kafka的group_id分组id */
  private String groupId;

  public KafkaBean() {
    InputStream inputStream = getClass().getResourceAsStream("/my.properties");
    Properties prop = new Properties();
    try {
      prop.load(inputStream);
      this.bootstrapServers = prop.getProperty("kafka.bootstrap.servers");
      this.keySerializer = prop.getProperty("kafka.key.serializer");
      this.valueSerializer = prop.getProperty("kafka.value.serializer");
      this.groupId = prop.getProperty("kafka.group.id");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
