package com.flink.stream.real.entity.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 流量结果类
 * @author: lingjian
 * @create: 2020/6/23 9:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowResult {

  /** 设备类型 */
  private String device;
  /** 来源类型 */
  private String source;
  /** 浏览量 */
  private Long pv;
  /** 访客数 */
  private Long uv;
  /** 创建时间 */
  private String windowEnd;
}
