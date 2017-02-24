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

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.RubyRailsPage;
import org.sonar.api.web.UserRole;

@NavigationSection({NavigationSection.RESOURCE})
@UserRole(UserRole.CODEVIEWER)
public class DoxygenPage extends AbstractRubyTemplate implements RubyRailsPage {

  @Override
  protected String getTemplatePath() {

    return "/org/sonar/plugins/doxygen/documentation_page.html.erb";
  }

  @Override
  public String getId() {
    return getClass().getName();
  }

  @Override
  public String getTitle() {
    return "Documentation";
  }

}
