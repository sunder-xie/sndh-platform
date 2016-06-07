package com.nhry.utils;

public class PrimaryKeyUtils {
  
  /**
   * 生成32位唯一的uuid
   * @return
   */
  public static String generateUuidKey(){
	  return java.util.UUID.randomUUID().toString().replace("-", "");
  }
}
