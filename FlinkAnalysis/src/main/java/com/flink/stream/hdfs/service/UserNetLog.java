package com.flink.stream.hdfs.service;

import java.util.ArrayList;
import java.util.List;

import com.flink.stream.hdfs.entity.UserLogCount;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * @description: 自定义处理方法，统计访问量最大的url，排序输出
 * @author: lingjian
 * @create: 2020/5/18 14:00
 */
public class UserNetLog extends KeyedProcessFunction<Tuple, UserLogCount, UserLogCount> {

  /** 定义状态变量 */
  private ListState<UserLogCount> urlState;

  @Override
  public void open(Configuration parameters) throws Exception {
    super.open(parameters);
    // 定义状态变量
    urlState =
        getRuntimeContext().getListState(new ListStateDescriptor<>("urlState", UserLogCount.class));
  }

  @Override
  public void processElement(UserLogCount value, Context ctx, Collector<UserLogCount> out)
      throws Exception {
    urlState.add(value);
    // 注册定时器，windowed+10秒触发
    ctx.timerService().registerEventTimeTimer(value.getWindowEnd() + 10 * 1000);
  }

  @Override
  public void onTimer(long timestamp, OnTimerContext ctx, Collector<UserLogCount> out)
      throws Exception {
    super.onTimer(timestamp, ctx, out);
    // 获取状态中所有的url访问量
    List<UserLogCount> list = new ArrayList<>();
    for (UserLogCount netWork : urlState.get()) {
      list.add(netWork);
    }
    // 情况urlState
    urlState.clear();
    // 按照访问量排序输出
    list.sort((o1, o2) -> (int) (o2.getCount() - o1.getCount()));
    // 将排名信息格式化为String
    list.forEach(out::collect);
  }
}
