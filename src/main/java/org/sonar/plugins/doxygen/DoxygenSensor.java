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

package org.sonar.plugins.doxygen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.resources.Project;

public class DoxygenSensor implements Sensor {

  public static final Logger LOGGER = LoggerFactory.getLogger(DoxygenSensor.class.getName());
  private final Settings settings;

  public DoxygenSensor(final Settings settings) {
    this.settings = settings;  
  }

  private boolean shouldExecute() {
    return this.settings.getBoolean(DoxygenPlugin.DOXYGEN_ENABLE_PROPERTY_KEY);
  }  

  @Override
  public void analyse(Project project, SensorContext context) {
    if(!shouldExecute()) {
      context.saveMeasure(new Measure(DoxygenMetrics.DISPLAY_DOC, "false"));   
      return;
    }
    
    if(this.settings.getString(DoxygenPlugin.DEPLOYMENT_URL) == null || this.settings.getString(DoxygenPlugin.DEPLOYMENT_URL).equals("")) {
      LOGGER.error("The global property '{}' is not set. Set it in SONAR and run another analysis.", DoxygenPlugin.DEPLOYMENT_URL);      
      context.saveMeasure(new Measure(DoxygenMetrics.ERROR_MESSAGE, "\"The global property '" + DoxygenPlugin.DEPLOYMENT_URL + "' is not set. Set it in SONAR and run another analysis."));
      return;
    } else {
      LOGGER.info("The global property '{}' set to '{}'.", DoxygenPlugin.DEPLOYMENT_URL, this.settings.getString(DoxygenPlugin.DEPLOYMENT_URL));    
    }
    
    Measure measure = new Measure(DoxygenMetrics.DISPLAY_DOC);
    measure.setData("true");
    measure.setPersistenceMode(PersistenceMode.DATABASE);    
    context.saveMeasure(measure);
    
    measure = new Measure(DoxygenMetrics.DOCUMENTATION_URL);
    measure.setData(this.settings.getString(DoxygenPlugin.DEPLOYMENT_URL));
    measure.setPersistenceMode(PersistenceMode.DATABASE);    
    context.saveMeasure(measure);
    
  }

  @Override
  public boolean shouldExecuteOnProject(Project project) {
    return this.settings.getBoolean(DoxygenPlugin.DOXYGEN_ENABLE_PROPERTY_KEY);
  }
}
