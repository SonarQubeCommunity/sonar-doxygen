/*
 * Sonar Doxygen Plugin
 * Copyright (C) 2012 David FRANCOIS
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

package org.sonar.plugins.doxygen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
