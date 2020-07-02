package com.bigdata.demo.entity;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 测试基类
 * @author: lingjian
 * @create: 2020/6/3 14:43
 */
@Data
@Entity
@Table(name = "demo_count")
public class Demo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /** 创建时间 */
  @Column(name = "create_time")
  private String createTime;
  /** 浏览量 */
  private Long pv;
  /** 访客数 */
  private Long uv;
}
