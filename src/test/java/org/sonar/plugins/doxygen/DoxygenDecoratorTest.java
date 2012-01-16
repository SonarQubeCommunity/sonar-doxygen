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
