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

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class UtilsTest {

  @Test
  public void getFormattedPathTest() {
    assertEquals(Utils.getFormattedPath("http://localhost:9000"), "http://localhost:9000");
    assertEquals(Utils.getFormattedPath("http://localhost:9000/"), "http://localhost:9000");
    assertEquals(Utils.getFormattedPath("http://localhost:80/sonar"), "http://localhost:80/sonar");
    assertEquals(Utils.getFormattedPath("http://localhost:80/sonar/"), "http://localhost:80/sonar");
  }

  @Test
  public void getBooleanValueTest() {
    PropertiesConfiguration config = new PropertiesConfiguration();
    assertFalse(Utils.getBooleanValue(config, Constants.CLASS_GRAPH, Constants.CLASS_GRAPH_DV));
    config.setProperty(Constants.CLASS_GRAPH, "toto");
    assertFalse(Utils.getBooleanValue(config, Constants.CLASS_GRAPH, Constants.CLASS_GRAPH_DV));
    config.setProperty(Constants.CLASS_GRAPH, "false");
    assertFalse(Utils.getBooleanValue(config, Constants.CLASS_GRAPH, Constants.CLASS_GRAPH_DV));
    config.setProperty(Constants.CLASS_GRAPH, "FALSE");
    assertFalse(Utils.getBooleanValue(config, Constants.CLASS_GRAPH, Constants.CLASS_GRAPH_DV));
    config.setProperty(Constants.CLASS_GRAPH, "true");
    assertTrue(Utils.getBooleanValue(config, Constants.CLASS_GRAPH, Constants.CLASS_GRAPH_DV));
    config.setProperty(Constants.CLASS_GRAPH, "TRUE");
    assertTrue(Utils.getBooleanValue(config, Constants.CLASS_GRAPH, Constants.CLASS_GRAPH_DV));
  }

}
