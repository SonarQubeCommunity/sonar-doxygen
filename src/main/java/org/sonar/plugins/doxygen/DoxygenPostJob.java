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

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.plugins.doxygen.exceptions.CheckException;
import org.sonar.plugins.doxygen.utils.Constants;
import org.sonar.plugins.doxygen.utils.DoxygenProject;
import org.sonar.plugins.doxygen.utils.Utils;

import java.io.File;

public class DoxygenPostJob implements PostJob {

  public static final Logger LOGGER = LoggerFactory.getLogger(DoxygenPostJob.class.getName());

  public static enum ExecutionEnum {
        DISABLE, KEEP, ENABLE
  }

  private ExecutionEnum enabled;

  private String docGenerationPath;
  private String htmlPath;

  /**
   * @see org.sonar.api.batch.PostJob#executeOn(org.sonar.api.resources.Project, org.sonar.api.batch.SensorContext) 
   */
  public void executeOn(Project prjct, SensorContext sc) {

    if (checkAndInitParameter(sc, prjct)) {
      String confPath = buildConfPath(prjct, docGenerationPath);
      if (ExecutionEnum.DISABLE.equals(enabled)) {
        LOGGER.info("=== SUPPRESS PREVIOUS GENERATION ===");
        Utils.deleteDir(new File(confPath));
      } else if (ExecutionEnum.KEEP.equals(enabled)) {
        if (!new File(confPath).exists()) {
          sc.saveMeasure(new Measure(DoxygenMetrics.ERROR_MESSAGE,
                            "Keep documentation activated: But documentation has not been generated."));
        } else {
          sc.saveMeasure(new Measure(DoxygenMetrics.WARNING_MESSAGE,
                            "Keep documentation activated: The documentation is not up to date."));
        }
        sc.saveMeasure(new Measure(DoxygenMetrics.DISPLAY_DOC, "true"));
      } else if (ExecutionEnum.ENABLE.equals(enabled)) {
        LOGGER.info("=== SUPPRESS PREVIOUS GENERATION ===");
        Utils.deleteDir(new File(confPath));
        LOGGER.info("=== DOXYGEN EXECUTION ===");
        DoxygenProject project = new DoxygenProject(confPath, htmlPath);
        project.generateDoxygenDocumentation(prjct);

        sc.saveMeasure(new Measure(DoxygenMetrics.DISPLAY_DOC, "true"));
      }
    }

  }

  private boolean checkAndInitParameter(SensorContext sc, Project prjct) {
    boolean success = true;

    try {
      enabled = checkAndGetEnabled(prjct.getConfiguration());
      docGenerationPath = checkAndGetPath(prjct.getConfiguration(), Constants.DEPLOYMENT_PATH);
      if (ExecutionEnum.ENABLE.equals(enabled) || ExecutionEnum.KEEP.equals(enabled)) {
        checkUrl(prjct.getConfiguration(), Constants.DEPLOYMENT_URL);
        htmlPath = getHTMLCustomPath(prjct.getConfiguration());
      }
    } catch (CheckException ex) {
      success = false;
      sc.saveMeasure(new Measure(DoxygenMetrics.ERROR_MESSAGE,
                    ex.getMessage()));
    }

    return success;
  }

  private void checkUrl(Configuration config, String property) throws CheckException {

    if (StringUtils.isEmpty(config.getString(property))) {
      String message = "The global property '" + property + "' is not set. Set it in SONAR and run another analysis.";
      LOGGER.error(message);
      throw new CheckException(message);
    }

  }

  private String checkAndGetPath(Configuration config, String property) throws CheckException {
    String path = config.getString(property);
    String message = null;

    if (StringUtils.isEmpty(path)) {
      message = "The global property '" + property + "' is not set. Set it in SONAR and run another analysis.";
    } else {
      File file = new File(path);
      if (!file.exists()) {
        message = "The directory for property '" + property + "' is badly set. "
                        + "Set it correctly in SONAR and run another analysis.";
      }
    }

    if (message != null) {
      LOGGER.error(message);
      throw new CheckException(message);
    }

    return Utils.getFormattedPath(path);
  }

  private ExecutionEnum checkAndGetEnabled(Configuration config) throws CheckException {
    String temp = config.getString(Constants.GENERATE_DOC_EXECUTION, Constants.GENERATE_DOC_EXECUTION_DV);

    ExecutionEnum result = null;
    try {
      result = ExecutionEnum.valueOf(temp.toUpperCase());
    } catch (IllegalArgumentException e) {
      String message = "The project property '" + Constants.GENERATE_DOC_EXECUTION + "' is badly set. "
                    + "Set it correctly in SONAR and run another analysis.";
      LOGGER.error(message);
      throw new CheckException(message);
    }

    return result;
  }

  // ============================================================================================
  // ================================== CONTROLS METHODS ========================================
  // ============================================================================================
  private String getHTMLCustomPath(final Configuration config) {
    String path = config.getString(Constants.CUSTOM_PATH, null);

    // if (path != null) {
    // TODO: contrôler l'existence des fichiers header.html, footer.html, doxygen.css
    // }

    return path;
  }

  private String buildConfPath(final Project project, final String outputPath) {
    StringBuilder builder = new StringBuilder();
    builder.append(outputPath);
    builder.append("/");
    builder.append(Constants.REPOSITORY_OUTPUT_DIR);
    builder.append("/");
    builder.append(project.getName());

    return builder.toString();
  }

}
