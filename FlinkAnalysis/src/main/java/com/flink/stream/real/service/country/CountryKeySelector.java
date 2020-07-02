package com.flink.stream.real.service.country;

import com.flink.stream.real.entity.country.CountryLog;
import com.flink.stream.utils.DateUtils;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @description: 自定义分组
 * @author: lingjian
 * @create: 2020/6/3 9:02
 */
public class CountryKeySelector
    implements KeySelector<Tuple2<CountryLog, Long>, Tuple2<String, String>> {
  @Override
  public Tuple2<String, String> getKey(Tuple2<CountryLog, Long> value) throws Exception {
    return Tuple2.of(
        DateUtils.longToString(value.f0.getCreateTime(), "yyyy-MM-dd"), value.f0.getCountry());
  }
}
