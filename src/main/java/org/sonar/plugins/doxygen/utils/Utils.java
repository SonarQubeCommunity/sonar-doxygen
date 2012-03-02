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

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.resources.Project;

import java.io.File;
import java.io.IOException;

public final class Utils {

  public static final Logger LOGGER = LoggerFactory.getLogger(Utils.class.getName());

  /**
   * Don't instantiate this object
   */
  private Utils() {
  }

  // =========================================================================
  // SONAR PROJECT UTILS
  // =========================================================================
  public static String getSourcesPath(final Project project) {
    StringBuilder builder = new StringBuilder();

    if (project != null) {
      if (project.getModules() == null || project.getModules().isEmpty()) {
        if (project.getFileSystem().getSourceDirs() != null) {
          for (File source : project.getFileSystem().getSourceDirs()) {
            builder.append("\"").append(source.getAbsolutePath().replaceAll("\\\\", "/")).append("\" ");
          }
        }
      } else {
        for (Project module : project.getModules()) {
          builder.append(getSourcesPath(module));
        }
      }
    }

    return builder.toString();
  }

  // =========================================================================
  // SCRIPT MANAGEMENT UTILS
  // =========================================================================
  public static void executeCommand(final String[] command) {
    try {
      Process process = Runtime.getRuntime().exec(command);

      ThreadInputStream inputStream = new ThreadInputStream(process.getInputStream(), false);
      ThreadInputStream errorStream = new ThreadInputStream(process.getErrorStream(), true);

      inputStream.start();
      errorStream.start();
      while (inputStream.getState() != inputStream.getState().TERMINATED) {
        Thread.sleep(10);
      }

    } catch (IOException e) {
      LOGGER.error("executeDosCommand : " + e.getMessage());
    } catch (InterruptedException e) {
      LOGGER.error("executeDosCommand : " + e.getMessage());
    }
  }

  /**
   * Load a boolean property configuration.
   * If the property is null, return the default value.
   * If the property is badly set, log a error.
   * 
   * @param config The configuration
   * @param property The property
   * @param defaultValue The default value
   * @return Return the value of property
   */
  public static boolean getBooleanValue(Configuration config, String property, boolean defaultValue) {
    boolean result = false;
    try {
      result = config.getBoolean(property, defaultValue);
    } catch (ConversionException e) {
      LOGGER.error("The Project property '" + property + "' is badly set. "
                    + "Set correctly this property in SONAR");
    }

    return result;
  }

  public static boolean deleteDir(File dir) {
    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }

    // The directory is now empty so delete it
    return dir.delete();
  }

  public static String getFormattedPath(String path) {
    String result = path;
    if (path.charAt(path.length() - 1) == '/') {
      result = path.substring(0, path.length() - 1);
    }

    return result;
  }
}
