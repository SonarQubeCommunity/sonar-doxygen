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

import java.util.Arrays;
import java.util.List;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

public class DoxygenMetrics implements Metrics {

  public static final String DISPLAY_DOC_KEY = "display_doc";
  public static final Metric DISPLAY_DOC = new Metric.Builder(DISPLAY_DOC_KEY, "Display doc", Metric.ValueType.STRING)
      .setDescription("Display the doxygen documentation")
      .setDirection(0)
      .setQualitative(false)
      .setDomain(CoreMetrics.DOMAIN_GENERAL)
      .create();
  
  public static final String DOCUMENTATION_URL_KEY = "documentation_url";
  public static final Metric DOCUMENTATION_URL = new Metric.Builder(DOCUMENTATION_URL_KEY, "Documentation url", Metric.ValueType.STRING)
      .setDescription("Url of documentation")
      .setDirection(Metric.DIRECTION_NONE)
      .setQualitative(false)
      .setDomain(CoreMetrics.DOMAIN_GENERAL)
      .create();
  
  @Override
  public List<Metric> getMetrics() {
    return Arrays.asList(DOCUMENTATION_URL, DISPLAY_DOC);
  }
}
