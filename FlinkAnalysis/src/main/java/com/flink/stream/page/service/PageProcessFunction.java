package com.flink.stream.page.service;

import java.util.ArrayList;
import java.util.List;

import com.flink.stream.page.entity.Page;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * @description: 页面停留时长处理
 * @author: lingjian
 * @create: 2020/6/19 14:26
 */
public class PageProcessFunction extends KeyedProcessFunction<String, Page, String> {

  // 收集所有的记录
  private ListState<Page> pageState;

  @Override
  public void open(Configuration parameters) throws Exception {
    super.open(parameters);
    // 定义状态变量
    pageState =
        getRuntimeContext().getListState(new ListStateDescriptor<>("pageState", Page.class));
  }

  @Override
  public void processElement(Page page, Context context, Collector<String> out) throws Exception {
    pageState.add(page);
  }

  @Override
  public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
    super.onTimer(timestamp, ctx, out);

    // 获取状态中所有的流量记录
    List<Page> list = new ArrayList<>();
    for (Page page : pageState.get()) {
      list.add(page);
    }

    // 清除pageState
    pageState.clear();

    out.collect(list.toString());
  }
}
