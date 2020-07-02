package com.flink.stream.hdfs.controller;

import com.alibaba.fastjson.JSONObject;
import com.flink.stream.real.entity.real.RealLog;
import com.flink.stream.hdfs.entity.UserLogCount;
import com.flink.stream.hdfs.service.UserJdbcSink;
import com.flink.stream.hdfs.service.UserNetLog;
import com.flink.stream.hdfs.service.UserCountAggregate;
import com.flink.stream.hdfs.service.UserWindowResultFunction;
import com.flink.stream.properties.Hdfs;
import com.flink.stream.utils.DateUtils;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * @description: hdfs的测试类
 * @author: lingjian
 * @create: 2020/6/3 15:16
 */
public class HdfsController {

  public static void main(String[] args) throws Exception {
    // 创建flink环境
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    // 设置时间格式
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
    // 设置文件夹名称
    String flieName = DateUtils.longToString(System.currentTimeMillis(), "yy-MM-dd");
    // 设置输入源 - hdfs
    DataStreamSource<String> stream = env.readTextFile(new Hdfs().getHdfsFilePath() + flieName);
    // 转化自定义pojo类
    DataStream<RealLog> dataStream =
        stream.map(data -> JSONObject.parseObject(data, RealLog.class));
    // 执行操作
    DataStream<UserLogCount> result =
        dataStream
            .assignTimestampsAndWatermarks(
                new AscendingTimestampExtractor<RealLog>() {
                  @Override
                  public long extractAscendingTimestamp(RealLog userLog) {
                    return userLog.getCreateTime();
                  }
                })
            .keyBy("url")
            .timeWindow(Time.minutes(60))
            .aggregate(new UserCountAggregate(), new UserWindowResultFunction())
            .keyBy("windowEnd")
            .process(new UserNetLog());

    // 输出打印
    result.printToErr("result");
    result.addSink(new UserJdbcSink());

    // 运行程序
    env.execute("hadoop source");
  }
}
