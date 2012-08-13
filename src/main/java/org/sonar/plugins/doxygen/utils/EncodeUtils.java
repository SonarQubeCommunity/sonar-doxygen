/*
 * Sonar Doxygen Plugin
 * Copyright (C) 2012 David FRANCOIS
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sonar.plugins.doxygen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public final class EncodeUtils {

  public static final Logger LOGGER = LoggerFactory.getLogger(EncodeUtils.class.getName());

  public static final Map<Character, String> doxygenCharToEncodeMap = initDoxygenCharToEncodeMap();

  public static final Map<Character, String> folderCharToEndodemap = initFolderCharToEncodeMap();

  private EncodeUtils() {

  }

  public static String encodeProjectName(String projectName) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < projectName.length(); i++) {
      builder.append(encodeCharacter(projectName.charAt(i), folderCharToEndodemap, false));
    }

    return builder.toString();
  }

  public static String encodeDoxygenFileName(final String name, final String prefix) {
    StringBuilder builder = new StringBuilder(prefix);
    for (int i = 0; i < name.length(); i++) {
      builder.append(encodeCharacter(name.charAt(i), doxygenCharToEncodeMap, true));
    }

    String result = null;
    if (builder.length() >= 128) {
      result = builder.substring(0, 96) + encodeMd5(builder.toString());
    } else {
      result = builder.toString();
    }
    return result;
  }

  private static String encodeCharacter(char c, Map<Character, String> map, boolean manageUppercase) {
    String result = map.get(c);
    if (result == null) {
      if (manageUppercase && Character.isUpperCase(c)) {
        result = "_" + Character.toLowerCase(c);
      } else {
        result = "" + c;
      }
    }
    return result;
  }

  private static String encodeMd5(String password) {
    byte[] uniqueKey = password.getBytes();
    byte[] hash = null;

    try {
      hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
    } catch (NoSuchAlgorithmException e) {
      LOGGER.error("No MD5 support in this VM.");
    }

    StringBuilder hashString = new StringBuilder();
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(hash[i]);
      if (hex.length() == 1) {
        hashString.append('0');
        hashString.append(hex.charAt(hex.length() - 1));
      } else {
        hashString.append(hex.substring(hex.length() - 2));
      }
    }
    return hashString.toString();
  }

  private static Map<Character, String> initDoxygenCharToEncodeMap() {
    Map<Character, String> map = new HashMap<Character, String>();
    map.put(Character.valueOf('_'), "__");
    map.put(Character.valueOf('-'), "-");
    map.put(Character.valueOf(':'), "_1");
    map.put(Character.valueOf('/'), "_2");
    map.put(Character.valueOf('<'), "_3");
    map.put(Character.valueOf('>'), "_4");
    map.put(Character.valueOf('*'), "_5");
    map.put(Character.valueOf('&'), "_6");
    map.put(Character.valueOf('|'), "_7");
    map.put(Character.valueOf('.'), "_8");
    map.put(Character.valueOf('!'), "_9");
    map.put(Character.valueOf(','), "_00");
    map.put(Character.valueOf(' '), "_01");
    map.put(Character.valueOf('{'), "_02");
    map.put(Character.valueOf('}'), "_03");
    map.put(Character.valueOf('?'), "_04");
    map.put(Character.valueOf('^'), "_05");
    map.put(Character.valueOf('%'), "_06");
    map.put(Character.valueOf('('), "_07");
    map.put(Character.valueOf(')'), "_08");
    map.put(Character.valueOf('+'), "_09");
    map.put(Character.valueOf('='), "_0A");
    map.put(Character.valueOf('$'), "_0B");

    return map;
  }

  private static Map<Character, String> initFolderCharToEncodeMap() {
    Map<Character, String> map = new HashMap<Character, String>();
    map.put(Character.valueOf('\\'), "_1");
    map.put(Character.valueOf('/'), "_2");
    map.put(Character.valueOf(':'), "_3");
    map.put(Character.valueOf('*'), "_4");
    map.put(Character.valueOf('?'), "_5");
    map.put(Character.valueOf('"'), "_6");
    map.put(Character.valueOf('<'), "_7");
    map.put(Character.valueOf('>'), "_8");
    map.put(Character.valueOf('|'), "_9");

    return map;
  }

}
