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

package org.sonar.plugins.doxygen.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.resources.Project;

public final class Utils {

    public static final Logger LOGGER = LoggerFactory.getLogger(Utils.class.getName());

    /**
     * Don't instantiate this object
     */
    private Utils() {
    }

    // =========================================================================
    //                            SONAR PROJECT UTILS
    // =========================================================================
    public static String getSourcesPath(final Project project) {
        StringBuilder builder = new StringBuilder();

        if (project != null) {
            if (project.getModules() == null || project.getModules().isEmpty()) {
                if (project.getFileSystem().getSourceDirs() != null) {
                    for (File source : project.getFileSystem().getSourceDirs()) {
                        builder.append("\"").append(source.getAbsolutePath().replaceAll("\\\\", "/")).append("\" ");
                    }
                }
            } else {
                for (Project module : project.getModules()) {
                    builder.append(getSourcesPath(module));
                }
            }
        }

        return builder.toString();
    }

    // =========================================================================
    //                         SCRIPT MANAGEMENT UTILS
    // =========================================================================
    public static void executeDosCommand(final String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);

            ThreadInputStream inputStream = new ThreadInputStream(process.getInputStream(), false);
            ThreadInputStream errorStream = new ThreadInputStream(process.getErrorStream(), true);

            inputStream.start();
            errorStream.start();
            while (inputStream.getState() != inputStream.getState().TERMINATED) {
                Thread.sleep(10);
            }

        } catch (IOException e) {
            LOGGER.error("executeDosCommand : " + e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.error("executeDosCommand : " + e.getMessage());
        }
    }

    /**
     * Load a boolean property configuration.
     * If the property is null, return the default value.
     * If the property is badly set, log a error.
     * 
     * @param config The configuration
     * @param property The property
     * @param defaultValue The default value
     * @return Return the value of property
     */
    public static boolean getBooleanValue(Configuration config, String property, boolean defaultValue) {
        boolean result = false;
        try {
            result = config.getBoolean(property, defaultValue);
        } catch (ConversionException e) {
            LOGGER.error("The Project property '" + property + "' is badly set. "
                    + "Set correctly this property in SONAR");
        }

        return result;
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public static String getFormattedPath(String path) {
        String result = path;
        if (path.charAt(path.length() - 1) == '/') {
            result = path.substring(0, path.length() - 1);
        }

        return result;
    }
}
