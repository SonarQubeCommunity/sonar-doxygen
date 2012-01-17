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

import org.sonar.api.Plugin;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.plugins.doxygen.utils.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * This class is the entry point for all extensions
 */
@Properties({

@Property(key = Constants.DEPLOYMENT_URL, name = Constants.DEPLOYMENT_URL_NAME,
        description = Constants.DEPLOYMENT_URL_DESC),

@Property(key = Constants.DEPLOYMENT_PATH, name = Constants.DEPLOYMENT_PATH_NAME,
        description = Constants.DEPLOYMENT_PATH_DESC),

@Property(key = Constants.GENERATE_DOC_EXECUTION, defaultValue = Constants.GENERATE_DOC_EXECUTION_DV,
        name = Constants.GENERATE_DOC_EXECUTION_NAME, description = Constants.GENERATE_DOC_EXECUTION_DESC,
        global = false, project = true),

@Property(key = Constants.EXCLUDE_FILES, name = Constants.EXCLUDE_FILES_NAME,
        description = Constants.EXCLUDE_FILES_DESC, global = false, project = true),

@Property(key = Constants.CUSTOM_PATH, name = Constants.CUSTOM_PATH_NAME,
        description = Constants.CUSTOM_PATH_DESC, project = true),

@Property(key = Constants.CLASS_GRAPH, defaultValue = Constants.CLASS_GRAPH_DV + "",
        name = Constants.CLASS_GRAPH_NAME, global = false, project = true),

@Property(key = Constants.CALL_GRAPH, defaultValue = Constants.CALL_GRAPH_DV + "",
        name = Constants.CALL_GRAPH_NAME, global = false, project = true),

@Property(key = Constants.CALLER_GRAPH, defaultValue = Constants.CALLER_GRAPH_DV + "",
        name = Constants.CALLER_GRAPH_NAME, global = false, project = true)

})
public class DoxygenPlugin implements Plugin {

  /**
   * @deprecated this is not used anymore
   */
  public String getKey() {
    return "Documentation";
  }

  /**
   * @deprecated this is not used anymore
   */
  public String getName() {
    return "Plugin Documentation";
  }

  /**
   * @deprecated this is not used anymore
   */
  public String getDescription() {
    return "Generate project documentation";
  }

  // This is where you're going to declare all your Sonar extensions
  public List getExtensions() {
    return Arrays.asList(DoxygenMetrics.class, DoxygenPostJob.class, DoxygenPage.class,
                DoxygenTab.class, DoxygenDecorator.class);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
