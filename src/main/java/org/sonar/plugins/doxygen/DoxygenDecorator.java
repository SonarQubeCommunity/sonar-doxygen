/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2009 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.doxygen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.plugins.doxygen.utils.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class DoxygenDecorator implements Decorator {

  public static final Logger LOGGER = LoggerFactory.getLogger(DoxygenDecorator.class.getName());

  public static final String PACKAGE = "namespace";
  public static final String CLASS = "class";

  private String projectName;

  private Map<Character, String> map;

  private Language language;

  /**
   * @see org.sonar.api.batch.Decorator#shouldExecuteOnProject(org.sonar.api.resources.Project) 
   */
  public boolean shouldExecuteOnProject(Project project) {
    language = project.getLanguage();
    projectName = getRootProjectName(project);
    initMap();

    return Java.INSTANCE.equals(language);
  }

  /**
   * @see org.sonar.api.batch.Decorator#decorate(org.sonar.api.resources.Resource, org.sonar.api.batch.DecoratorContext) 
   */
  public void decorate(Resource rsrc, DecoratorContext dc) {
    boolean ok = false;
    String tampon = null;

    if (ResourceUtils.isRootProject(rsrc) || ResourceUtils.isModuleProject(rsrc)) {
      tampon = "index.html";
      ok = true;
    } else if (ResourceUtils.isPackage(rsrc)) {
      String name = rsrc.getName();
      if (Java.INSTANCE.equals(language)) {
        name = name.replaceAll("\\.", "::");
      }
      tampon = encodeDoxygenFileName(name, PACKAGE) + ".html";
      ok = true;
    } else if (ResourceUtils.isFile(rsrc)) {
      String name = rsrc.getLongName();
      if (Java.INSTANCE.equals(language)) {
        name = name.replaceAll("\\.", "::");
      }
      tampon = encodeDoxygenFileName(name, CLASS) + ".html";
      ok = true;
    }

    if (ok) {
      StringBuilder builder = new StringBuilder();
      builder.append(Constants.REPOSITORY_OUTPUT_DIR);
      builder.append("/");
      builder.append(projectName);
      builder.append("/html/");
      builder.append(tampon);
      dc.saveMeasure(new Measure(DoxygenMetrics.DOXYGEN_URL, builder.toString()));
    }
  }

  private String getRootProjectName(Project project) {
    if (project.getParent() == null) {
      return project.getName();
    } else {
      return getRootProjectName(project.getParent());
    }
  }

  private String encodeCharacter(char c) {
    String result = map.get(c);
    if (result == null) {
      if (Character.isUpperCase(c)) {
        result = "_" + Character.toLowerCase(c);
      } else {
        result = "" + c;
      }
    }
    return result;
  }

  public String encodeDoxygenFileName(final String name, final String prefix) {
    StringBuilder builder = new StringBuilder(prefix);
    for (int i = 0; i < name.length(); i++) {
      builder.append(encodeCharacter(name.charAt(i)));
    }

    String result = null;
    if (builder.length() >= 128) {
      result = builder.substring(0, 96) + encodeMd5(builder.toString());
    } else {
      result = builder.toString();
    }
    return result;
  }

  private String encodeMd5(String password) {
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

  public void initMap() {
    map = new HashMap<Character, String>();
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
  }

}
