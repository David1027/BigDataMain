package com.flink.stream.real.entity.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 国家返回结果类
 * @author: lingjian
 * @create: 2020/6/22 11:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResult {

  /** 国家名称 */
  private String country;
  /** 浏览量 */
  private Long pv;
  /** 访客数 */
  private Long uv;
  /** 创建时间 */
  private String windowEnd;
}
