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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadInputStream extends Thread {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(ThreadInputStream.class.getName());

    private InputStream stream;
    private boolean errorStream;

    public ThreadInputStream(InputStream stream, boolean errorStream) {
        this.stream = stream;
        this.errorStream = errorStream;
    }

    public void run() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (errorStream) {
                    LOGGER.error(line);
                }

            }
        } catch (IOException e) {
            LOGGER.error("executeDosCommand : " + e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                LOGGER.error("executeDosCommand : " + e.getMessage());
            }

        }
    }
}

