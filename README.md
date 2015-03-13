# Doxygen Plugin

Download and Versions information: http://update.sonarsource.org/plugins/doxygen-confluence.html

## Description / Features
This plugin generates the documentation of the application using Doxygen and Graphviz. To generate graphs, Graphviz must be installed.
This generated documentation can be browse through the Documentation item on the left menu or from the Documentation tab at file level.
* The _Documentation_ item is available at project level (project, module and package). Different pages of documentation are displayed according to the level of the current dashboard:
  * At project or module level: the main page of the documentation is displayed.
  * At package level: the main page of the package documentation is displayed.
* From there, it is then possible to navigate through the whole documentation.
* The _Documentation_ tab displays the documentation associated to the current class.
* From there, it is then possible to navigate through the whole documentation.

As the documentation is generated in HTML, it is necessary to make it accessible from an URL.
SonarQube server can be used but it is not recommended as it may affect SonarQube performances. To configure it that way, set Web Server Deployment URL to http://localhost:9000 (may be different according to your configuration) and Documentation Path Generation to <sonar.install.dir>/war/sonar-server.
As said before, it is recommended to deploy documentation on another web server. Apache could be used. In this way, set Server Deployment URL to http://localhost:80 (may be different according to your configuration) and Documentation Path Generation to <apache.install.dir>/www.

### Global Properties

<table>
<tr><th></th><th>Property Name</th><th>Mandatory</th><th>Comments</td></tr>
<tr><td>sonar.doxygen.deploymentPath</td><td>Documentation Path Generation</td><td>YES</td><td>Directory path where the documentation will be generated.<br/> If SonarQube server is used to access the documentation, the path should be set to: <sonar.install.dir>/war/sonar-server.</td></tr>
<tr><td>sonar.doxygen.deploymentUrl</td><td>Web Server Deployment URL</td><td>YES</td><td>URL to display the generated documentation.<br/>SonarQube server can be used to access the documentation.</td></tr>
<tr><td>sonar.doxygen.customPath</td><td>Directory Path containing header.html, footer.html and doxygen.css</td><td> NO</td><td>In order to customize HTML documentation.</td></tr>
</table>

### Project Properties
<table>
<tr><th></th><th>Property Name</th><th>Mandatory</th><th>Comments</th><th> Default Value</th></tr>
<tr><td>sonar.doxygen.generateDocumentation</td><td>Generate Doxygen Documentation</td><td>NO</td><td>
Possible values:
* disable: do not generate documentation and delete existing documentation.
* keep: do not generate documentation but keep previous documentation if existing.
* enable: generate or regenerate documentation
</td><td>disable</td></tr>
<tr><td>sonar.doxygen.excludeFiles</td><td>Excludes Specific Files</td><td>NO</td><td>Coma separated list</td><td></td></tr>
<tr><td>sonar.doxygen.generateClassGraphs</td><td>Generates Class Graphs</td><td>NO</td><td>If the property is set to true, graphviz must be installed.</td><td>false</td></tr>
<tr><td>sonar.doxygen.generateCallGraphs</td><td>Generates Call Graphs</td><td>NO</td><td>If the property is set to true, graphviz must be installed.</td><td>false</td></tr>
<tr><td>sonar.doxygen.generateCallerGraphs</td><td>Generates Caller Graphs</td><td>NO</td><td>If the property is set to true, graphviz must be installed.</td><td>false</td></tr>
</table>

### Requirements

<table>
<tr><td>Plugin</td><td>0.1</td></tr>
<tr><td>Doxygen</td><td> 1.7.5</td></tr>
<tr><td>Graphviz</td><td> 2.28</td></tr>
</table>

### Usage & Installation
1. Install the following tools (standard installation):
  * Doxygen	http://www.stack.nl/~dimitri/doxygen/download.html
  * Graphviz 	http://www.graphviz.org/Download.php

2. Add the directories <doxygen.install.dir>/bin and <graphviz.install.dir>/bin as Path Environment Variables. Commands doxygen, dot, etc... must be recognized by the system.
2. Stop SonarQube server.
3. Copy the JAR file to the <sonar.install.dir>/extensions/plugins/ directory.
4. Restart SonarQube server.
5. Configure the plugin at global level and for each project.

### Known Limitations
This first version is limited to Java.
