package org.example;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @ParameterizedTest
    @MethodSource("calculateSource")
    void calculateSuccess(CalculateTestObject testObject) {
        assertEquals(testObject.result, homeWork.calculate(testObject.inputString));
    }

    private List<CalculateTestObject> calculateSource() {
        return List.of(
                new CalculateTestObject("( 8 + 2 * 5 ) / ( 1 + 3 * 2 - 4 )", 6.0),
                new CalculateTestObject("( 7 + pow(2,2) * 5 ) / ( 1 + 3 * 2 - 4 )", 9.0),
                new CalculateTestObject("pow(1,2)", 1.0),
                new CalculateTestObject("pow(2,3) + sqr(5)", 33.0),
                new CalculateTestObject("4 + ( sin(0) + cos(0) )", 5.0),
                new CalculateTestObject("8 * ( 9 + ( sin(0) / cos(0) ) )", 72.0)
        );
    }

    private static class CalculateTestObject{
        String inputString;
        double result;

        public CalculateTestObject(String inputString, double result) {
            this.inputString = inputString;
            this.result = result;
        }
    }

}