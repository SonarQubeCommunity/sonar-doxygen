/*
 * SonarQube Doxygen Plugin
 * Copyright (C) 2012 SonarSource
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

public final class Constants {

  // =========================================================================
  // DOXYGEN GENERATE DOC EXECUTION
  // =========================================================================
  public static final String GENERATE_DOC_EXECUTION = "sonar.doxygen.generateDocumentation";
  public static final String GENERATE_DOC_EXECUTION_DV = "disable";
  public static final String GENERATE_DOC_EXECUTION_NAME = "Generates Doxygen Documentation";
  public static final String GENERATE_DOC_EXECUTION_DESC =
      "Possible values: <br/>"
        + "<ul class='bullet'><li><i>disable</i>: do not generate documentation and delete existing documentation.</li>"
        + "<li><i>keep</i>: do not generate documentation but keep previous documentation if existing.</li>"
        + "<li><i>enable</i>: generate or regenerate documentation.</li></ul><br />";

  // =========================================================================
  // WEB SERVER DEPLOYMENT URL
  // =========================================================================
  public static final String DEPLOYMENT_URL = "sonar.doxygen.deploymentUrl";
  public static final String DEPLOYMENT_URL_NAME = "Web Server Deployment URL";
  public static final String DEPLOYMENT_URL_DESC =
      "URL to display the generated documentation. <br />"
        + "Sonar server can be used to access the documentation.";
  
  // =========================================================================
  // Doxygen Custom Properties
  // =========================================================================
  public static final String DOXYGEN_PROPERTIES_PATH = "sonar.doxygenProperties.path";
  public static final String DOXYGEN_PROPERTIES_PATH_DV = "";
  public static final String DOXYGEN_PROPERTIES_PATH_NAME = "Path to doxygen properties";
  public static final String DOXYGEN_PROPERTIES_PATH_DESC =
      "Path to doxygen properties file. It will overwrite only the default values. <br />";
  
  
  // =========================================================================
  // WEB SERVER DEPLOYMENT PATH
  // =========================================================================
  public static final String DEPLOYMENT_PATH = "sonar.doxygen.deploymentPath";
  public static final String DEPLOYMENT_PATH_NAME = "Documentation Path Generation";
  public static final String DEPLOYMENT_PATH_DESC =
      "Directory path where the documentation will be generated.<br />"
        + "If Sonar server is used to access the documentation, the path should be set to: <br />"
        + "<i>&lt;sonar.installation.dir&gt;/war/sonar-server</i>.";

  // =========================================================================
  // EXCLUDES FILES
  // =========================================================================
  public static final String EXCLUDE_FILES = "sonar.doxygen.excludeFiles";
  public static final String EXCLUDE_FILES_NAME = "Excludes Specific files";
  public static final String EXCLUDE_FILES_DESC = "Coma separated list";

  // =========================================================================
  // CUSTOM PATH
  // =========================================================================
  public static final String CUSTOM_PATH = "sonar.doxygen.customPath";
  public static final String CUSTOM_PATH_NAME = "Directory path containing header.html, footer.html and doxygen.css";
  public static final String CUSTOM_PATH_DESC = "In order to customize HTML documentation.";

  // =========================================================================
  // CLASS GRAPH
  // =========================================================================
  public static final String CLASS_GRAPH = "sonar.doxygen.generateClassGraphs";
  public static final boolean CLASS_GRAPH_DV = false;
  public static final String CLASS_GRAPH_NAME = "Generates Class Graphs";

  // =========================================================================
  // CALL GRAPH
  // =========================================================================
  public static final String CALL_GRAPH = "sonar.doxygen.generateCallGraphs";
  public static final boolean CALL_GRAPH_DV = false;
  public static final String CALL_GRAPH_NAME = "Generates Call Graphs";

  // =========================================================================
  // CALLER GRAPH
  // =========================================================================
  public static final String CALLER_GRAPH = "sonar.doxygen.generateCallerGraphs";
  public static final boolean CALLER_GRAPH_DV = false;
  public static final String CALLER_GRAPH_NAME = "Generates Caller Graphs";

  
  
  /**
   * Extension for doxygen configuration file
   */
  public static final String CONFIG_EXTENSION = ".properties";

  /**
   * Name of the default doxygen configuration file
   */
  public static final String DEFAULT_CONFIG_NAME = "default_config" + CONFIG_EXTENSION;

  /**
   * Name of the final doxygen configuration file
   */
  public static final String CONFIG_NAME = "config" + CONFIG_EXTENSION;

  /**
   * Name of the final doxygen configuration file
   */
  public static final String DOXYGEN_DOMAIN = "Doxygen";

  public static final String REPOSITORY_OUTPUT_DIR = "documentation";

  public static final String DOXYGEN_COMMAND = "doxygen";

  public static final String ERROR_MESSAGE = "error_message";
  public static final String WARNING_MESSAGE = "warning_message";

  public static final String ENABLED = "YES";
  public static final String DISABLED = "NO";

  private Constants() {

  }

}
