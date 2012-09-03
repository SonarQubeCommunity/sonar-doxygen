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
    name = Constants.CALLER_GRAPH_NAME, global = false, project = true),

  @Property(key = Constants.DOXYGEN_PATH, defaultValue = Constants.DOXYGEN_PATH_DV + "",
    name = Constants.DOXYGEN_PATH_NAME),

  @Property(key = Constants.DOXYGEN_PROPERTIES_PATH, defaultValue = Constants.DOXYGEN_PROPERTIES_PATH_DV + "",
    name = Constants.DOXYGEN_PROPERTIES_PATH_NAME, description = Constants.DOXYGEN_PROPERTIES_PATH_DESC,
    global = false, project = true)     
})
public class DoxygenPlugin implements Plugin {

  /**
   * @deprecated this is not used anymore
   */
  @Deprecated
  public String getKey() {
    return "Documentation";
  }

  /**
   * @deprecated this is not used anymore
   */
  @Deprecated
  public String getName() {
    return "Plugin Documentation";
  }

  /**
   * @deprecated this is not used anymore
   */
  @Deprecated
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
