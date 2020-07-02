package com.flink.stream.real.entity.real;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用户行为日志
 * @author: lingjian
 * @create: 2020/6/2 9:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealLog {
  /** 页面标题 */
  private String title;
  /** 页面url */
  private String url;
  /** 跳转页面url */
  private String ref;
  /** ip地址 */
  private String ip;
  /** 用户id */
  private Integer userId;
  /** cookie中的访客id */
  private String uvId;
  /** session中的访问id，会话id */
  private String ssId;
  /** session有效期内访问页面的次数 */
  private Integer ssCount;
  /** session访问时间，会话时间 */
  private Long ssTime;
  /** 设备 */
  private String agent;
  /** 平台 */
  private String plat;
  /** 停留时长 */
  private String timePage;
  /** 创建时间 */
  private Long createTime;
}
