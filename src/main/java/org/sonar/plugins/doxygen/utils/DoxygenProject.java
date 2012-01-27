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

package org.sonar.plugins.doxygen.utils;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.resources.Project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DoxygenProject {

  public static final Logger LOGGER = LoggerFactory.getLogger(DoxygenProject.class.getName());

  private String confPath;

  private String htmlCustomPath;

  public DoxygenProject(final String confPath, final String htmlCustomPath) {
    this.confPath = confPath;
    this.htmlCustomPath = htmlCustomPath;
  }

  public void generateDoxygenDocumentation(final Project project) {

    if (generateDoxygenConfiguration(project)) {
      LOGGER.info("### Generating documentation ###");
      String[] command = {Constants.DOXYGEN_COMMAND, confPath + "/" + Constants.CONFIG_NAME};
      Utils.executeCommand(command);
    }
  }

  public boolean generateDoxygenConfiguration(Project project) {
    LOGGER.info("### Generating configuration ###");
    final String defaultConfigPath = confPath + "/" + Constants.DEFAULT_CONFIG_NAME;
    final String configPath = confPath + "/" + Constants.CONFIG_NAME;

    // if documentation directory doesn't exist in target directory,
    // create it
    File file = new File(confPath);
    if (!file.exists() && !file.mkdirs()) {
      return false;
    }

    // The default configuration file has already been generated?
    // If not, generation of the default configuration.
    file = new File(defaultConfigPath);
    if (!file.exists()) {
      generateDefaultConfiguration(defaultConfigPath);
    }

    // if file config.properties exist in target\documentation directory,
    // suppress it
    file = new File(configPath);
    if (file.exists() && !file.delete()) {
      LOGGER.error("An error occurred when deleting the configuration file.");
      return false;
    }

    try {
      // Create final configuration file
      Map<String, String> properties = initProperties(project.getConfiguration());
      properties.put("PROJECT_NAME", project.getName());
      properties.put("OUTPUT_DIRECTORY", "\"" + confPath + "\"");
      properties.put("INPUT", Utils.getSourcesPath(project));
      generateConfiguration(defaultConfigPath, configPath, properties);
    } catch (IOException e) {
      LOGGER.error("An error occurred when deleting the configuration file : " + e);
      return false;
    }

    return true;
  }

  private void generateDefaultConfiguration(final String path) {
    String[] command = {Constants.DOXYGEN_COMMAND, "-s", "-g", path};
    Utils.executeCommand(command);
  }

  private void generateConfiguration(final String defaultPath, final String path, final Map<String, String> properties)
      throws IOException {

    // Generate configuration file
    File configFile = new File(path);
    if (!configFile.exists()) {
      configFile.createNewFile();
    }

    BufferedReader reader = null;
    BufferedWriter writer = null;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(defaultPath)));
      writer = new BufferedWriter(new FileWriter(path));

      String ligne;
      while ((ligne = reader.readLine()) != null) {
        int equalsPosition = ligne.indexOf('=');
        // If we treat a property line
        if (equalsPosition != -1) {
          for (String propertyKey : properties.keySet()) {
            if (propertyKey.equals(ligne.substring(0, equalsPosition).trim())) {
              ligne = ligne.substring(0, equalsPosition) + "= " + properties.get(propertyKey);
              break;
            }
          }
        }

        writer.write(ligne);
        writer.newLine();
        writer.flush();
      }
    } finally {
      try {
        reader.close();
      } finally {
        writer.close();
      }
    }

  }

  private Map<String, String> initProperties(final Configuration config) {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put("EXTRACT_ALL", "YES");
    properties.put("RECURSIVE", "YES");
    properties.put("GENERATE_LATEX", "NO");
    properties.put("COLLABORATION_GRAPH", "NO");
    properties.put("GROUP_GRAPH", "NO");
    properties.put("INCLUDE_GRAPH", "NO");
    properties.put("INCLUDED_GRAPH", "NO");
    properties.put("GRAPHICAL_HIERARCHY", "NO");
    properties.put("DIRECTORY_GRAPH", "NO");
    properties.put("OPTIMIZE_OUTPUT_JAVA", "YES");
    properties.put("TAB_SIZE", "4");
    properties.put("FILE_PATTERNS", "*.java");
    properties.put("HTML_TIMESTAMP", "NO");
    properties.put("CLASS_DIAGRAMS", "NO");

    if (htmlCustomPath != null) {
      properties.put("HTML_HEADER", htmlCustomPath + "/header.html");
      properties.put("HTML_FOOTER", htmlCustomPath + "/footer.html");
      properties.put("HTML_STYLESHEET", htmlCustomPath + "/doxygen.css");
    }

    final String[] excludes = config.getStringArray(Constants.EXCLUDE_FILES);

    if (excludes != null && excludes.length != 0) {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < excludes.length; i++) {
        builder.append(excludes[i]).append(" ");
      }
      properties.put("EXCLUDE_PATTERNS", builder.toString());
    }

    boolean withDot = false;

    if (Utils.getBooleanValue(config, Constants.CLASS_GRAPH, Constants.CLASS_GRAPH_DV)) {
      properties.put("CLASS_GRAPH", "YES");
      withDot = true;
    } else {
      properties.put("CLASS_GRAPH", "NO");
    }

    if (Utils.getBooleanValue(config, Constants.CALL_GRAPH, Constants.CALL_GRAPH_DV)) {
      properties.put("CALL_GRAPH", "YES");
      withDot = true;
    } else {
      properties.put("CALL_GRAPH", "NO");
    }

    if (Utils.getBooleanValue(config, Constants.CALLER_GRAPH, Constants.CALLER_GRAPH_DV)) {
      properties.put("CALLER_GRAPH", "YES");
      withDot = true;
    } else {
      properties.put("CALLER_GRAPH", "NO");
    }

    if (withDot) {
      properties.put("HAVE_DOT", "YES");
      properties.put("DOT_NUM_THREADS", "4");
      properties.put("DOT_FONTSIZE", "7");
      properties.put("DOT_CLEANUP", "NO");
    }

    return properties;
  }

}
