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
