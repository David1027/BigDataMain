package com.flink.stream.hdfs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 流量统计基类
 * @author: lingjian
 * @create: 2020/5/18 13:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogCount {
  /** 请求url */
  private String url;
  /** 窗口时间 */
  private Long windowEnd;
  /** 流量次数 */
  private Long count;
}
