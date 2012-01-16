/*
 * Sonar DOXYGEN Plugin.
 * Copyright (C) 2009 SonarSource
 * dev@sonar.codehaus.org
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

import java.util.Arrays;
import java.util.List;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

public class DoxygenMetrics implements Metrics {

    /* metrics definition for logica abacus. */
    public static final String DOXYGEN_URL_KEY = "doxygen_url";
    public static final Metric DOXYGEN_URL = new Metric(DOXYGEN_URL_KEY, "Doxygen Url",
            "URL of Doxygen Documentation.", Metric.ValueType.DATA, -1, false,
            CoreMetrics.ABSTRACTNESS_KEY);
    
    public static final String WARNING_MESSAGE_KEY = "warning_message";
    public static final Metric WARNING_MESSAGE = new Metric(WARNING_MESSAGE_KEY, "Warning Message",
            "Warning Message", Metric.ValueType.DATA, -1, false, CoreMetrics.ABSTRACTNESS_KEY);
    
    public static final String ERROR_MESSAGE_KEY = "error_message";
    public static final Metric ERROR_MESSAGE = new Metric(ERROR_MESSAGE_KEY, "Error Message",
            "Error Message", Metric.ValueType.DATA, -1, false, CoreMetrics.ABSTRACTNESS_KEY);
    
    public static final String DISPLAY_DOC_KEY = "display_doc";
    public static final Metric DISPLAY_DOC = new Metric(DISPLAY_DOC_KEY, "Display doc",
            "Display the doxygen documentation", Metric.ValueType.BOOL, -1, false, CoreMetrics.ABSTRACTNESS_KEY);

    // getMetrics() method is defined in the Metrics interface and is used by
    // Sonar to retrieve the list of new Metric
    public List<Metric> getMetrics() {
        return Arrays.asList(DOXYGEN_URL, WARNING_MESSAGE, ERROR_MESSAGE, DISPLAY_DOC);
    }
}

