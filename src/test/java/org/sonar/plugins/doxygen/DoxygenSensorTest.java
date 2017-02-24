/*
 * SonarQube Doxygen Plugin
 * Copyright (c) 2012-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
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

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

/**
 *
 * @author jocs
 */
public class DoxygenSensorTest {
  @Test
  public void testNotShouldExecutePostJobWhenUrlNotSet() throws Exception {
    Settings conf = new Settings();
    SensorContext ctx = mock(SensorContext.class);
    conf.appendProperty(DoxygenPlugin.DOXYGEN_ENABLE_PROPERTY_KEY, "true");
    DoxygenSensor job = new DoxygenSensor(conf);
    job.analyse(mock(Project.class), ctx);
    ArgumentCaptor<Measure> measure = ArgumentCaptor.forClass(Measure.class);
    
    verify(ctx).saveMeasure(measure.capture());
    
    assertEquals("error_message", measure.getValue().getMetricKey());
  }
  
  @Test
  public void testNotShouldExecutePostJobWhenPropNotSet() throws Exception {
    Settings conf = new Settings();
    SensorContext ctx = mock(SensorContext.class);
    conf.appendProperty(DoxygenPlugin.DOXYGEN_ENABLE_PROPERTY_KEY, "false");
    DoxygenSensor job = new DoxygenSensor(conf);
    job.analyse(mock(Project.class), ctx);
    
    ArgumentCaptor<Measure> measure = ArgumentCaptor.forClass(Measure.class);
    
    verify(ctx).saveMeasure(measure.capture());
    
    assertEquals("display_doc", measure.getValue().getMetricKey());
    assertEquals("false", measure.getValue().getData());
    
  }  
  
  @Test
  public void testShouldExecutePostJobWhenPropSet() throws Exception {
    Settings conf = new Settings();
    SensorContext ctx = mock(SensorContext.class);
    conf.appendProperty(DoxygenPlugin.DOXYGEN_ENABLE_PROPERTY_KEY, "true");
    conf.appendProperty(DoxygenPlugin.DEPLOYMENT_URL, "http://server");
    DoxygenSensor job = new DoxygenSensor(conf);
    job.analyse(mock(Project.class), ctx);
    ArgumentCaptor<Measure> measure = ArgumentCaptor.forClass(Measure.class);
    
    verify(ctx, times(2)).saveMeasure(measure.capture());
    
    List<Measure> capturedMeasure = measure.getAllValues();
    
    assertEquals("display_doc", capturedMeasure.get(0).getMetricKey());
    assertEquals("true", capturedMeasure.get(0).getData());
    assertEquals(DoxygenMetrics.DOCUMENTATION_URL_KEY, capturedMeasure.get(1).getMetricKey());
    assertEquals("http://server", capturedMeasure.get(1).getData());
    
  }  
}
