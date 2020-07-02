package com.flink.stream.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Data;

/**
 * @description: hdfs配置类
 * @author: lingjian
 * @create: 2020/6/5 9:50
 */
@Data
public class Hdfs {

  /** hdfs的文件地址 */
  private String hdfsFilePath;

  public Hdfs() {
    InputStream inputStream = getClass().getResourceAsStream("/my.properties");
    Properties prop = new Properties();
    try {
      prop.load(inputStream);
      this.hdfsFilePath = prop.getProperty("hdfs.file.path");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
