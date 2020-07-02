package com.flink.stream.utils.country;

import java.util.*;

public class MyStringUtils {
  /**
   * @title isMatch3
   * @author Lijie HelloBox@outlook.com
   * @params [reg, s1]
   * @returns boolean
   * @updateTime 2019-05-03 16:22
   * @throws
   * @description 基于动态规划 通配符比较 reg 匹配的表达式 s1需要匹配的字符串
   */
  public static boolean isMatch3(String reg, String s1) {
    int countXing = 0;
    for (char c : reg.toCharArray()) {
      countXing++;
    }
    if (reg.length() - countXing > s1.length()) {
      return false;
    }
    boolean[][] dp = new boolean[reg.length() + 1][s1.length() + 1];
    dp[0][0] = true;
    for (int i = 1; i <= reg.length(); i++) {
      char s2_char = reg.charAt(i - 1);
      dp[i][0] = dp[i - 1][0] && s2_char == '*';
      for (int j = 1; j <= s1.length(); j++) {
        char s1_char = s1.charAt(j - 1);
        if (s2_char == '*') {
          dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
        } else {
          dp[i][j] = dp[i - 1][j - 1] && (s2_char == '?' || s1_char == s2_char);
        }
      }
    }
    return dp[reg.length()][s1.length()];
  }
  /**
   * @title isMatch
   * @author Lijie HelloBox@outlook.com
   * @params [s, p]
   * @returns boolean
   * @updateTime 2019-05-03 21:15
   * @throws
   * @description s 源字符串，P表达式
   */
  public static boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    Boolean[] cur = new Boolean[s.length() + 1];
    for (int i = 0; i <= m; i++) {
      cur[i] = false;
    }
    cur[0] = true;
    for (int j = 1; j <= n; j++) {
      // use the value before update
      boolean pre = cur[0];
      cur[0] = cur[0] && p.charAt(j - 1) == '*';
      for (int i = 1; i <= m; i++) {
        // record the value before update
        boolean temp = cur[i];
        if (p.charAt(j - 1) != '*') {
          cur[i] = pre && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?');
        } else {
          cur[i] = cur[i - 1] || cur[i];
        }
        pre = temp;
      }
    }
    return cur[m];
  }

  // 字符串 编辑路径
  public static int LD(String st1, String st2) {
    int[][] ints = new int[st1.length() + 1][st2.length() + 1];
    for (int i = 0; i <= st1.length(); i++) {
      ints[i][0] = i;
    }
    for (int j = 0; j <= st2.length(); j++) {
      ints[0][j] = j;
    }
    for (int i = 1; i <= st1.length(); i++) {
      for (int j = 1; j <= st2.length(); j++) {
        ints[i][j] =
            st1.charAt(i - 1) == st2.charAt(j - 1)
                ? ints[i - 1][j - 1]
                : Math.min(Math.min(ints[i - 1][j], ints[i][j - 1]), ints[i - 1][j - 1]) + 1;
      }
    }
    return ints[st1.length()][st2.length()];
  }

  // 基于正则的 编辑路径
  public static boolean isMatch2(String reg, String t) {
    String str1 = reg;
    String str2 = t;
    int[][] dp = new int[str1.length() + 1][str2.length() + 1];
    for (int i = 1; i < str1.length() + 1; i++) {
      for (int j = 1; j < str2.length() + 1; j++) {
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }
    if (str1.replaceAll("[?*]", "").length() == dp[str1.length()][str2.length()]) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(String str) {
    return str == null || "".equals(str);
  }

  public static String replace(String inString, String oldPattern, String newPattern) {
    if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
      int index = inString.indexOf(oldPattern);
      if (index == -1) {
        return inString;
      } else {
        int capacity = inString.length();
        if (newPattern.length() > oldPattern.length()) {
          capacity += 16;
        }

        StringBuilder sb = new StringBuilder(capacity);
        int pos = 0;

        for (int patLen = oldPattern.length();
            index >= 0;
            index = inString.indexOf(oldPattern, pos)) {
          sb.append(inString.substring(pos, index));
          sb.append(newPattern);
          pos = index + patLen;
        }

        sb.append(inString.substring(pos));
        return sb.toString();
      }
    } else {
      return inString;
    }
  }

  public static boolean hasLength(String str) {
    return str != null && !str.isEmpty();
  }

  public static String cleanPath(String path) {
    if (!hasLength(path)) {
      return path;
    } else {
      String pathToUse = replace(path, "\\", "/");
      int prefixIndex = pathToUse.indexOf(58);
      String prefix = "";
      if (prefixIndex != -1) {
        prefix = pathToUse.substring(0, prefixIndex + 1);
        if (prefix.contains("/")) {
          prefix = "";
        } else {
          pathToUse = pathToUse.substring(prefixIndex + 1);
        }
      }

      if (pathToUse.startsWith("/")) {
        prefix = prefix + "/";
        pathToUse = pathToUse.substring(1);
      }

      String[] pathArray = delimitedListToStringArray(pathToUse, "/");
      LinkedList<String> pathElements = new LinkedList();
      int tops = 0;

      int i;
      for (i = pathArray.length - 1; i >= 0; --i) {
        String element = pathArray[i];
        if (!".".equals(element)) {
          if ("..".equals(element)) {
            ++tops;
          } else if (tops > 0) {
            --tops;
          } else {
            pathElements.add(0, element);
          }
        }
      }

      for (i = 0; i < tops; ++i) {
        pathElements.add(0, "..");
      }

      if (pathElements.size() == 1 && "".equals(pathElements.getLast()) && !prefix.endsWith("/")) {
        pathElements.add(0, ".");
      }

      return prefix + collectionToDelimitedString(pathElements, "/");
    }
  }

  public static String[] delimitedListToStringArray(String str, String delimiter) {
    return delimitedListToStringArray(str, delimiter, (String) null);
  }

  public static String collectionToDelimitedString(Collection<?> coll, String delim) {
    return collectionToDelimitedString(coll, delim, "", "");
  }

  public static String collectionToCommaDelimitedString(Collection<?> coll) {
    return collectionToDelimitedString(coll, ",");
  }

  public static String[] delimitedListToStringArray(
      String str, String delimiter, String charsToDelete) {
    if (str == null) {
      return new String[0];
    } else if (delimiter == null) {
      return new String[] {str};
    } else {
      List<String> result = new ArrayList();
      int pos;
      if (delimiter.isEmpty()) {
        for (pos = 0; pos < str.length(); ++pos) {
          result.add(deleteAny(str.substring(pos, pos + 1), charsToDelete));
        }
      } else {
        int delPos;
        for (pos = 0;
            (delPos = str.indexOf(delimiter, pos)) != -1;
            pos = delPos + delimiter.length()) {
          result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
        }

        if (str.length() > 0 && pos <= str.length()) {
          result.add(deleteAny(str.substring(pos), charsToDelete));
        }
      }

      return toStringArray((Collection) result);
    }
  }

  public static String collectionToDelimitedString(
      Collection<?> coll, String delim, String prefix, String suffix) {
    if (coll == null || coll.size() == 0) {
      return "";
    } else {
      StringBuilder sb = new StringBuilder();
      Iterator it = coll.iterator();

      while (it.hasNext()) {
        sb.append(prefix).append(it.next()).append(suffix);
        if (it.hasNext()) {
          sb.append(delim);
        }
      }

      return sb.toString();
    }
  }

  public static String deleteAny(String inString, String charsToDelete) {
    if (hasLength(inString) && hasLength(charsToDelete)) {
      StringBuilder sb = new StringBuilder(inString.length());

      for (int i = 0; i < inString.length(); ++i) {
        char c = inString.charAt(i);
        if (charsToDelete.indexOf(c) == -1) {
          sb.append(c);
        }
      }

      return sb.toString();
    } else {
      return inString;
    }
  }

  public static String[] toStringArray(Collection<String> collection) {
    return collection != null ? (String[]) collection.toArray(new String[0]) : new String[0];
  }

  public static String[] toStringArray(Enumeration<String> enumeration) {
    return enumeration != null
        ? toStringArray((Collection) Collections.list(enumeration))
        : new String[0];
  }

  public static String applyRelativePath(String path, String relativePath) {
    int separatorIndex = path.lastIndexOf("/");
    if (separatorIndex != -1) {
      String newPath = path.substring(0, separatorIndex);
      if (!relativePath.startsWith("/")) {
        newPath = newPath + "/";
      }

      return newPath + relativePath;
    } else {
      return relativePath;
    }
  }

  public static String getFilename(String path) {
    if (path == null) {
      return null;
    } else {
      int separatorIndex = path.lastIndexOf("/");
      return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
    }
  }
}
