package com.flink.stream.real.entity.country;

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
public class CountryLog {
  /** 国家名称 */
  private String country;
  /** ip地址 */
  private String ip;
  /** 用户id */
  private Integer userId;
  /** cookie中的访客id */
  private String uvId;
  /** 平台 */
  private String plat;
  /** 创建时间 */
  private Long createTime;
}
