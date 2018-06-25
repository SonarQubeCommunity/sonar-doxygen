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

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import org.sonar.api.Plugin;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

public class DoxygenPlugin implements Plugin {
  
  public static final String DOXYGEN_ENABLE_PROPERTY_KEY = "sonar.doxygen.enable";
  public static final String DEPLOYMENT_URL = "sonar.doxygen.url";
    
  public static List<PropertyDefinition> generalProperties() {
    String subcateg = "General";
    return new ArrayList<>(Arrays.asList(
      PropertyDefinition
        .builder(DOXYGEN_ENABLE_PROPERTY_KEY)
        .defaultValue("false")
        .name("Disable Doxygen Import")
        .description("Whether or not the plugin should link the current project to a external doxygen documentation")
        .subCategory(subcateg)
        .type(PropertyType.BOOLEAN)
        .onQualifiers(Qualifiers.PROJECT)
        .index(1)
        .build(),
      PropertyDefinition            
        .builder(DEPLOYMENT_URL)
        .name("Link of documentation")
        .description("Documentation server url")
        .subCategory(subcateg)
        .onQualifiers(Qualifiers.PROJECT)
        .index(2)
        .build()
      ));
  }
  

  @Override
  public String toString() {
    return getClass().getSimpleName();
  } 

  @Override
  public void define(Context context) {
    List<Object> extensions = new ArrayList<>();
    extensions.add(DoxygenMetrics.class);
    extensions.add(DoxygenSensor.class);
    extensions.add(DoxygenPage.class);
    extensions.addAll(generalProperties());
    context.addExtensions(extensions);    
  }
}
