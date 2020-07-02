package com.flink.stream.real.entity.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 实时流量日志类
 * @author: lingjian
 * @create: 2020/6/23 9:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowLog {

  /** 页面标题 */
  private String title;
  /** 页面url */
  private String url;
  /** 跳转页面url */
  private String ref;
  /** 用户id */
  private Integer userId;
  /** cookie中的访客id */
  private String uvId;
  /** 设备 */
  private String agent;
  /** 设备来源：pc-电脑端，web-手机端，app-手机app端 */
  private String device;
  /** 流量来源：interview-自主访问，baidu-百度，google-谷歌，other-其他 */
  private String source;
  /** 创建时间 */
  private Long createTime;
}
