package com.flink.stream.utils.device;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 处理请求参数的工具类
 * @author: lingjian
 * @create: 2019/12/26 16:31
 */
public class HttpRequestUtils {

  private static final String UN_KNOWN = "unknown";
  private static final String USER_AGENT = "XwtViewUser-Agent";

  /**
   * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
   *
   * <p>可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
   * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
   *
   * <p>如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
   *
   * <p>用户真实IP为： 192.168.1.110
   *
   * @param request 请求信息
   * @return String ip地址
   */
  public static String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * 判断是否是手机端来源
   *
   * @param request 请求参数
   * @return Boolean
   */
  public static boolean isMobileDevice(HttpServletRequest request) {
    String requestHeader = request.getHeader("user-agent").toLowerCase();
    String[] deviceArray = new String[] {"android", "iphone", "ios", "windows phone"};
    if (requestHeader == null) {
      return false;
    }
    requestHeader = requestHeader.toLowerCase();
    for (int i = 0; i < deviceArray.length; i++) {
      if (requestHeader.indexOf(deviceArray[i]) > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * android : 所有android设备 mac os : iphone ipad windows phone:Nokia等windows系统的手机
   *
   * @param request 请求头
   * @return DeviceTypeEnum /** 电脑端-PC,移动端-WAP,app/苹果-IOS,app/安卓-ANDROID
   */
  public static String getDevice(HttpServletRequest request) {
    String requestHeader = request.getHeader("user-agent").toLowerCase();
    String[] deviceArray = new String[] {"android", "iphone", "ios", "windows phone"};
    requestHeader = requestHeader.toLowerCase();
    for (int i = 0; i < deviceArray.length; i++) {
      if (requestHeader.indexOf(deviceArray[i]) > 0) {
        return "wap";
      }
    }
    return "pc";
  }

  /**
   * android : 所有android设备 mac os : iphone ipad windows phone:Nokia等windows系统的手机
   *
   * @param agent 用户设备标识
   * @return 设备类型 /** 电脑端-pc,移动端-wap,app端-app
   */
  public static String getDevice(String agent) {
    String[] deviceArray = new String[] {"android", "iphone", "ios", "windows phone"};
    agent = agent.toLowerCase();
    for (int i = 0; i < deviceArray.length; i++) {
      if (agent.indexOf(deviceArray[i]) > 0) {
        return "wap";
      }
    }
    return "pc";
  }

  /**
   * 判断是否为微信
   *
   * @param request
   * @return
   */
  public static boolean isWeChat(HttpServletRequest request) {
    // 判断 是否是微信浏览器
    String userAgent = request.getHeader("user-agent").toLowerCase();
    if (userAgent.indexOf("micromessenger") > -1) {
      return true;
    } else {
      return false;
    }
  }
}
