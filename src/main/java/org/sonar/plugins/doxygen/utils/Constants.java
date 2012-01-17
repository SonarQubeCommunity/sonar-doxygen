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
  // WEB SERVER DEPLOYMENT PATH
  // =========================================================================
  public static final String DEPLOYMENT_PATH = "sonar.doxygen.deploymentPath";
  public static final String DEPLOYMENT_PATH_NAME = "Documentation Path Generation";
  public static final String DEPLOYMENT_PATH_DESC =
            "Directory path where the documentation will be generated.<br />"
              + "If Sonar server is used to access the documentation, the path should be set to: <br />"
              + "<i><sonar.installation.dir>/war/sonar-server</i>.";

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

  public static final String REPOSITORY_OUTPUT_DIR = "documentation";

  public static final String DOXYGEN_COMMAND = "doxygen";

  public static final String ERROR_MESSAGE = "error_message";
  public static final String WARNING_MESSAGE = "warning_message";

  private Constants() {

  }

}
