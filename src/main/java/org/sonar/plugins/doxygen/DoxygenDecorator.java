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
package org.sonar.plugins.doxygen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.plugins.doxygen.utils.Constants;
import org.sonar.plugins.doxygen.utils.EncodeUtils;

public class DoxygenDecorator implements Decorator {

    public static final Logger LOGGER = LoggerFactory.getLogger(DoxygenDecorator.class.getName());
    public static final String PACKAGE = "namespace";
    public static final String CLASS = "class";
    private String projectName;
    private Language language;

    /**
     * @see
     * org.sonar.api.batch.Decorator#shouldExecuteOnProject(org.sonar.api.resources.Project)
     */
    public boolean shouldExecuteOnProject(Project project) {
        language = project.getLanguage();
        projectName = getRootProjectName(project);

        LOGGER.debug("lang Decorator for: " + language);
        LOGGER.debug("aSet Decorator for: " + language.toString());
        LOGGER.debug("key Decorator for: " + Java.KEY);
        
        if (isLanguageSupported(language.toString())) {
            LOGGER.debug("Set Decorator for: " + language);
            return true;
        }
        return false;
    }

    public void decorate(Resource rsrc, DecoratorContext dc) {
        String tampon = null;

        if (Java.INSTANCE.equals(language)) {
            tampon = createJavaTampon(rsrc, tampon);
        }
        if (language.toString().equals("c++")) {
            tampon = createCppTampon(rsrc, tampon);
        }

        if (tampon != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(Constants.REPOSITORY_OUTPUT_DIR);
            builder.append("/");
            builder.append(EncodeUtils.encodeProjectName(projectName));
            builder.append("/html/");
            builder.append(tampon);

            LOGGER.debug("DoxDecorator: Context Name: " + rsrc.getName());            
            LOGGER.debug("DoxDecorator: Context Long Name: " + rsrc.getLongName());
            LOGGER.debug("DoxDecorator: Link Found : " + builder.toString());

            dc.saveMeasure(new Measure(DoxygenMetrics.DOXYGEN_URL, builder.toString()));
        }
    }

    private boolean isLanguageSupported(String language) {
        if (language.equalsIgnoreCase("c++")
                || language.equalsIgnoreCase(Java.KEY)) {
            return true;
        }
        return false;
    }

    private String createCppTampon(Resource rsrc, String tampon) {
        if (ResourceUtils.isRootProject(rsrc) || ResourceUtils.isModuleProject(rsrc)) {
            tampon = "index.html";

        } else {
            String name = rsrc.getName();
            LOGGER.debug("ResourceUtils.isFile(rsrc): DoxDecorator: name: " + name);
            tampon = EncodeUtils.encodeDoxygenFileName(name, "") + ".html";
        }

        return tampon;
    }

    private String createJavaTampon(Resource rsrc, String tampon) {
        if (ResourceUtils.isRootProject(rsrc) || ResourceUtils.isModuleProject(rsrc)) {
            tampon = "index.html";

        } else if (ResourceUtils.isPackage(rsrc)) {
            String name = rsrc.getName();
            name = name.replaceAll("\\.", "::");
            tampon = EncodeUtils.encodeDoxygenFileName(name, PACKAGE) + ".html";
        } else if (ResourceUtils.isFile(rsrc)) {
            String name = rsrc.getLongName();
            name = name.replaceAll("\\.", "::");
            tampon = EncodeUtils.encodeDoxygenFileName(name, CLASS) + ".html";
        }

        return tampon;
    }

    private String getRootProjectName(Project project) {
        if (project.getParent() == null) {
            return project.getName();
        } else {
            return getRootProjectName(project.getParent());
        }
    }
}
