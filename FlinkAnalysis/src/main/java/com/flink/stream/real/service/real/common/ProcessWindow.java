package com.flink.stream.real.service.real.common;

import java.util.Iterator;

import com.flink.stream.real.entity.real.RealResult;
import com.flink.stream.real.entity.real.RealLog;

import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.hash.BloomFilter;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.hash.Funnels;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @description: 案例结果处理类
 * @author: lingjian
 * @create: 2020/6/3 9:48
 */
public class ProcessWindow
    extends ProcessWindowFunction<Tuple2<RealLog, Long>, RealResult, String, TimeWindow> {

  private transient ValueState<BloomFilter> bloomFilterState;
  private transient ValueState<Long> pvState;
  private transient ValueState<Long> uvState;

  @Override
  public void open(Configuration parameters) throws Exception {
    super.open(parameters);
    StateTtlConfig ttlConfig =
        StateTtlConfig.newBuilder(Time.minutes(60 * 6))
            .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
            .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
            .build();
    ValueStateDescriptor<BloomFilter> bloomFilterDescriptor =
        new ValueStateDescriptor<>(
            "bloom_filter", TypeInformation.of(new TypeHint<BloomFilter>() {}));
    ValueStateDescriptor<Long> pvDescriptor = new ValueStateDescriptor<>("pv_count", Long.class);
    ValueStateDescriptor<Long> uvDescriptor = new ValueStateDescriptor<>("uv_count", Long.class);

    bloomFilterDescriptor.enableTimeToLive(ttlConfig);
    pvDescriptor.enableTimeToLive(ttlConfig);
    uvDescriptor.enableTimeToLive(ttlConfig);

    bloomFilterState = getRuntimeContext().getState(bloomFilterDescriptor);
    pvState = getRuntimeContext().getState(pvDescriptor);
    uvState = getRuntimeContext().getState(uvDescriptor);
  }

  @Override
  public void process(
      String s,
      Context context,
      Iterable<Tuple2<RealLog, Long>> iterable,
      Collector<RealResult> out)
      throws Exception {
    // 获取pv，uv的值
    Long pv = pvState.value();
    Long uv = uvState.value();
    BloomFilter bloomFilter = bloomFilterState.value();

    // 初始化
    if (bloomFilter == null) {
      bloomFilter = BloomFilter.create(Funnels.unencodedCharsFunnel(), 10 * 1000 * 1000);
      pv = 0L;
      uv = 0L;
    }

    Iterator<Tuple2<RealLog, Long>> mapIterator = iterable.iterator();
    while (mapIterator.hasNext()) {
      pv += 1;
      RealLog userLog = mapIterator.next().f0;
      if (!bloomFilter.mightContain(userLog.getUvId())) {
        bloomFilter.put(userLog.getUvId());
        uv += 1;
      }
    }

    bloomFilterState.update(bloomFilter);
    pvState.update(pv);
    uvState.update(uv);

    out.collect(new RealResult(s, pv, uv));
  }
}
