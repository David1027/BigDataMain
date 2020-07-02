package com.flink.stream.page.service;

import com.alibaba.fastjson.JSONObject;
import com.flink.stream.page.entity.Page;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;

/**
 * @description: 页面转化map方法
 * @author: lingjian
 * @create: 2020/6/19 14:01
 */
public class PageMap extends RichMapFunction<String, Page> {

  private transient ValueState<Page> pageState;

  @Override
  public Page map(String s) throws Exception {
    Page page = JSONObject.parseObject(s, Page.class);
    page.setTimeOnPage(0L);
    return page;
  }
}
