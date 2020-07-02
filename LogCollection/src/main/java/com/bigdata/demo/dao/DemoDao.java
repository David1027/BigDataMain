package com.bigdata.demo.dao;

import java.util.Optional;

import com.bigdata.demo.entity.Demo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: demo数据访问层
 * @author: lingjian
 * @create: 2020/6/3 14:46
 */
public interface DemoDao extends JpaRepository<Demo, Long> {

  Optional<Demo> findFirstByCreateTime(String time);
}
