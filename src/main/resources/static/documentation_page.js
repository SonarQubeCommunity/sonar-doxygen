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

var style = `@import url(https://fonts.googleapis.com/css?family=Raleway:300,700);

body {
  width:100%;
  height:100%;
  background:#48A9E6;
  font-family: 'Raleway', sans-serif;
  font-weight:300;
  margin:0;
  padding:0;
}

#title {
  text-align:center;
  font-size:40px;
  margin-top:40px;
  margin-bottom:-40px;
  position:relative;
  color:#fff;
}

.circles:after {
  content:'';
  display:inline-block;
  width:100%;
  height:100px;
  background:#fff;
  position:absolute;
  top:-50px;
  left:0;
  transform:skewY(-4deg);
  -webkit-transform:skewY(-4deg);
}

.circles { 
	background:#fff;
	text-align: center;
	position: relative;
  margin-top:-60px;
  box-shadow:inset -1px -4px 4px rgba(0,0,0,0.2);
}

.circles p {
	font-size: 240px;
	color: #fff;
	padding-top: 60px;
	position: relative;
  z-index: 9;
  line-height: 100%;
}

.circles p small { 
  font-size: 40px; 
  line-height: 100%; 
  vertical-align: top;   
}

.circles .circle.small {
	width: 140px;
	height: 140px;
	border-radius: 50%;
	background: #48A9E6;
	position: absolute;
	z-index: 1;
	top: 80px;
	left: 50%;
	animation: 7s smallmove infinite cubic-bezier(1,.22,.71,.98);	
	-webkit-animation: 7s smallmove infinite cubic-bezier(1,.22,.71,.98);
	animation-delay: 1.2s;
	-webkit-animation-delay: 1.2s;
}

.circles .circle.med {
	width: 200px;
	height: 200px;
	border-radius: 50%;
	background: #48A9E6;
	position: absolute;
	z-index: 1;
	top: 0;
	left: 10%;
	animation: 7s medmove infinite cubic-bezier(.32,.04,.15,.75);	
	-webkit-animation: 7s medmove infinite cubic-bezier(.32,.04,.15,.75);
	animation-delay: 0.4s;
	-webkit-animation-delay: 0.4s;
}

.circles .circle.big {
	width: 400px;
	height: 400px;
	border-radius: 50%;
	background: #48A9E6;
	position: absolute;
	z-index: 1;
	top: 200px;
	right: 0;
	animation: 8s bigmove infinite;	
	-webkit-animation: 8s bigmove infinite;
	animation-delay: 3s;
	-webkit-animation-delay: 1s;
}

@-webkit-keyframes smallmove {
	0% { top: 10px; left: 45%; opacity: 1; }
	25% { top: 300px; left: 40%; opacity:0.7; }
	50% { top: 240px; left: 55%; opacity:0.4; }
	75% { top: 100px; left: 40%;  opacity:0.6; }
	100% { top: 10px; left: 45%; opacity: 1; }
}
@keyframes smallmove {
	0% { top: 10px; left: 45%; opacity: 1; }
	25% { top: 300px; left: 40%; opacity:0.7; }
	50% { top: 240px; left: 55%; opacity:0.4; }
	75% { top: 100px; left: 40%;  opacity:0.6; }
	100% { top: 10px; left: 45%; opacity: 1; }
}

@-webkit-keyframes medmove {
	0% { top: 0px; left: 20%; opacity: 1; }
	25% { top: 300px; left: 80%; opacity:0.7; }
	50% { top: 240px; left: 55%; opacity:0.4; }
	75% { top: 100px; left: 40%;  opacity:0.6; }
	100% { top: 0px; left: 20%; opacity: 1; }
}

@keyframes medmove {
	0% { top: 0px; left: 20%; opacity: 1; }
	25% { top: 300px; left: 80%; opacity:0.7; }
	50% { top: 240px; left: 55%; opacity:0.4; }
	75% { top: 100px; left: 40%;  opacity:0.6; }
	100% { top: 0px; left: 20%; opacity: 1; }
}

@-webkit-keyframes bigmove {
	0% { top: 0px; right: 4%; opacity: 0.5; }
	25% { top: 100px; right: 40%; opacity:0.4; }
	50% { top: 240px; right: 45%; opacity:0.8; }
	75% { top: 100px; right: 35%;  opacity:0.6; }
	100% { top: 0px; right: 4%; opacity: 0.5; }
}
@keyframes bigmove {
	0% { top: 0px; right: 4%; opacity: 0.5; }
	25% { top: 100px; right: 40%; opacity:0.4; }
	50% { top: 240px; right: 45%; opacity:0.8; }
	75% { top: 100px; right: 35%;  opacity:0.6; }
	100% { top: 0px; right: 4%; opacity: 0.5; }
}
`;

function addStyleString(str,element) {
    var node = document.createElement('style');
    node.innerHTML = str;
    element.appendChild(node);
}

window.registerExtension('doxygen/documentation_page', function (options) {
  // let's create a flag telling if the page is still displayed
  console.log(options);

    var divFound = document.getElementById("notFoundId");
    if (divFound !== null) {        
        divFound.remove();
    }  

    var DoxygenFrameId = document.getElementById("DoxygenFrameId");
    if (DoxygenFrameId !== null) {        
        DoxygenFrameId.remove();
    }  

  // then do a Web API call to the /api/issues/search to get the number of issues
  // we pass `resolved: false` to request only unresolved issues
  // and `componentKeys: options.component.key` to request issues of the given project
  window.SonarRequest.getJSON('/api/measures/component', {
    metricKeys: 'documentation_url,display_doc',
    component: options.component.key
  }).then(function (response) {
    var isEnabled = true;
    var urlDoc = "";

    if (response.component.measures.length > 0 && response.component.measures[0].metric === "documentation_url") {
        isEnabled = response.component.measures[1].value;
        urlDoc = response.component.measures[0].value;
    }

    if (response.component.measures.length > 0 && response.component.measures[1].metric === "documentation_url") {
        isEnabled = response.component.measures[0].value;
        urlDoc = response.component.measures[1].value;
    }

    if (isEnabled === "true") {
        console.log(options.component.key);
        var parentElement = options.el.parentElement;
        var iframe = document.getElementById("DoxygenFrameId");
        if (iframe === null) {
            iframe = document.createElement('iframe');
            iframe.src = urlDoc;
            iframe.id = 'DoxygenFrameId';
            iframe.style = 'width:100%;height:800px';
            // append just created element to the container
            options.el.appendChild(iframe);
        }     
    } else {
        var divFound = document.getElementById("notFoundId");
        if (divFound === null) {
            var div = document.createElement('div');
            div.id = "notFoundId";
            div.innerHTML = `  <div id="not-found">
                <div class="circles">
                  <p>404<br>
                   <small>DOXYGEN NOT CONFIGURED</small>
                  </p>
                  <span class="circle big"></span>
                  <span class="circle med"></span>
                  <span class="circle small"></span>
                </div>
              </div>
            `;
            addStyleString(style, div);
            options.el.appendChild(div);
        }
    }
  });

  // return a function, which is called when the page is being closed
  return function () {
    
  };
});