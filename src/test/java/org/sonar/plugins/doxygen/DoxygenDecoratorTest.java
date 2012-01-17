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

package org.sonar.plugins.doxygen;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class DoxygenDecoratorTest {

  @Test
  public void encodeDoxygenFileNameTest() {
    DoxygenDecorator decorator = new DoxygenDecorator();
    decorator.initMap();
    String test = "com::test::NomDeMaClasse";
    String result = "com_1_1test_1_1_nom_de_ma_classe";
    assertEquals(decorator.encodeDoxygenFileName(test, DoxygenDecorator.CLASS), DoxygenDecorator.CLASS + result);
    test = "test::junit::avec::un::tres::long::nom::de::package::pour::tester::encodage::ClasseAvecUnLongNomDeClasse";
    result = "test_1_1junit_1_1avec_1_1un_1_1tres_1_1long_1_1nom_1_1de_1_1package_1_1pour_1_1tester_1_1enfaf088aefa3b8583d52e69c8dbffa2ad";
    assertEquals(decorator.encodeDoxygenFileName(test, DoxygenDecorator.CLASS), DoxygenDecorator.CLASS + result);
  }

}
