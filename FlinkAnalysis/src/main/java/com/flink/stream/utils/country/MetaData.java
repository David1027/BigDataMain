package com.flink.stream.utils.country;

import java.util.Map;

/** @copyright IPIP.net */

/**
 * {"build":1562137969,"ip_version":1,"languages":{"CN":0},
 * "node_count":451190,"total_size":3649744,"fields":["country_name","region_name","city_name"]
 */
public class MetaData {
  public int build;
  public int ip_version;
  public int node_count;
  public Map<String, Integer> languages;
  public String[] fields;
  public int total_size;
}
