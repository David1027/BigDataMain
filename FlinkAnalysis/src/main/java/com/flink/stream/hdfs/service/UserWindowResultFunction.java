package com.flink.stream.hdfs.service;

import com.flink.stream.hdfs.entity.UserLogCount;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @description: 将流量累加器聚合后的数据封装成样例输出
 * @author: lingjian
 * @create: 2020/5/18 13:57
 */
public class UserWindowResultFunction
    implements WindowFunction<Long, UserLogCount, Tuple, TimeWindow> {
  @Override
  public void apply(
      Tuple tuple, TimeWindow window, Iterable<Long> input, Collector<UserLogCount> out)
      throws Exception {
    String url = ((Tuple1<String>) tuple).f0;
    Long count = input.iterator().next();
    out.collect(new UserLogCount(url, window.getEnd(), count));
  }
}
