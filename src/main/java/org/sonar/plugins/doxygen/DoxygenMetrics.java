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

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.plugins.doxygen.utils.Constants;

import java.util.Arrays;
import java.util.List;

public class DoxygenMetrics implements Metrics {

  public static final String DOXYGEN_URL_KEY = "doxygen_url";
  public static final Metric DOXYGEN_URL = new Metric.Builder(DOXYGEN_URL_KEY, "Doxygen Url", Metric.ValueType.DATA)
      .setDescription("URL of Doxygen Documentation.")
      .setDirection(0)
      .setQualitative(false)
      .setDomain(Constants.DOXYGEN_DOMAIN)
      .create();

  public static final String WARNING_MESSAGE_KEY = "warning_message";
  public static final Metric WARNING_MESSAGE = new Metric.Builder(WARNING_MESSAGE_KEY, "Warning Message", Metric.ValueType.DATA)
      .setDescription("Warning Message")
      .setDirection(0)
      .setQualitative(false)
      .setDomain(Constants.DOXYGEN_DOMAIN)
      .create();

  public static final String ERROR_MESSAGE_KEY = "error_message";
  public static final Metric ERROR_MESSAGE = new Metric.Builder(ERROR_MESSAGE_KEY, "Error Message", Metric.ValueType.DATA)
      .setDescription("Error Message")
      .setDirection(0)
      .setQualitative(false)
      .setDomain(Constants.DOXYGEN_DOMAIN)
      .create();

  public static final String DISPLAY_DOC_KEY = "display_doc";
  public static final Metric DISPLAY_DOC = new Metric.Builder(DISPLAY_DOC_KEY, "Display doc", Metric.ValueType.BOOL)
      .setDescription("Display the doxygen documentation")
      .setDirection(0)
      .setQualitative(false)
      .setDomain(Constants.DOXYGEN_DOMAIN)
      .create();

  // getMetrics() method is defined in the Metrics interface and is used by
  // Sonar to retrieve the list of new Metric
  public List<Metric> getMetrics() {
    return Arrays.asList(DOXYGEN_URL, WARNING_MESSAGE, ERROR_MESSAGE, DISPLAY_DOC);
  }
}
