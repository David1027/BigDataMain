package com.flink.stream.real.controller;

import com.flink.stream.real.entity.country.CountryResult;
import com.flink.stream.real.entity.country.CountryLog;
import com.flink.stream.real.service.country.*;
import com.flink.stream.utils.kafka.KafkaUtils;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.TimeEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

/**
 * @description: 测试类，实现kafka连接
 * @author: lingjian
 * @create: 2020/6/2 9:43
 */
public class CountryController {

  public static void main(String[] args) throws Exception {
    // 创建flink环境
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    // 设置时间格式
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
    // 设置输入源 - kafka
    DataStreamSource<String> stream =
        env.addSource(
            new FlinkKafkaConsumer010<>(
                "log", new SimpleStringSchema(), KafkaUtils.getProperties()));
    // 转化自定义pojo类
    DataStream<CountryLog> dataStream = stream.map(new CountryMap());
    // 转换对象并设置时间
    DataStream<Tuple2<CountryLog, Long>> temp =
        dataStream
            // 转换日志对象
            .map(data -> Tuple2.of(data, 1L))
            .returns(Types.TUPLE(Types.POJO(CountryLog.class), Types.LONG))
            // 设置事件时间
            .assignTimestampsAndWatermarks(new CountryWatermark());
    // 使用BoomFilter窗口pv，uv计算
    DataStream<CountryResult> result =
        temp
            // 根据指定时间格式yyyy-MM-dd进行分组
            .keyBy(new CountryKeySelector())
            // 开滑动窗口
            .window(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
            .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(10)))
            .trigger(CountTrigger.of(1))
            .evictor(TimeEvictor.of(Time.seconds(0), true))
            .process(new CountryProcessWindow());

    // 输出打印
    result.printToErr();
    // 输出mysql
    result.addSink(new CountryJdbcSink());

    // 执行程序
    env.execute("kafka source");
  }
}
