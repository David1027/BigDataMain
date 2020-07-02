package com.flink.stream.real.entity.real;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: pv,uv的展示类
 * @author: lingjian
 * @create: 2020/6/2 15:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealResult {

  /** 创建时间 */
  private String createTime;
  /** 浏览量 */
  private Long pv;
  /** 访客数 */
  private Long uv;
}
