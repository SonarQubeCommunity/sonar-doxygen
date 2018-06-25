/*
 * SonarQube Doxygen Plugin
 * Copyright (c) 2012-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
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

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Configuration;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;

public class DoxygenSensor implements Sensor {

  public static final Logger LOGGER = LoggerFactory.getLogger(DoxygenSensor.class.getName());
  private final Configuration settings;

  public DoxygenSensor(final Configuration settings) {
    this.settings = settings;  
  }

  private boolean shouldExecute() {
    return this.settings.getBoolean(DoxygenPlugin.DOXYGEN_ENABLE_PROPERTY_KEY).get();
  }  
  
  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.global();
  }

  @Override
  public void execute(SensorContext context) {
    if(!shouldExecute()) {     
        context.<String>newMeasure()
          .forMetric(DoxygenMetrics.DISPLAY_DOC)
          .on(context.module())
          .withValue("false")
          .save();
      return;
    }
    
    if(!this.settings.get(DoxygenPlugin.DEPLOYMENT_URL).isPresent() || this.settings.get(DoxygenPlugin.DEPLOYMENT_URL).get().equals("")) {
      LOGGER.error("The global property '{}' is not set. Set it in SONAR and run another analysis.", DoxygenPlugin.DEPLOYMENT_URL);
        context.<String>newMeasure()
          .forMetric(DoxygenMetrics.DISPLAY_DOC)
          .on(context.module())
          .withValue("false")
          .save();
      return;
    } else {
      LOGGER.info("The global property '{}' set to '{}'.", DoxygenPlugin.DEPLOYMENT_URL, this.settings.get(DoxygenPlugin.DEPLOYMENT_URL).get());    
    }
    
    context.<String>newMeasure()
      .forMetric(DoxygenMetrics.DISPLAY_DOC)
      .on(context.module())
      .withValue("true")
      .save();
        
    context.<String>newMeasure()
      .forMetric(DoxygenMetrics.DOCUMENTATION_URL)
      .on(context.module())
      .withValue(this.settings.get(DoxygenPlugin.DEPLOYMENT_URL).get())
      .save();
  }
}
