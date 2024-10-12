package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void check() {
        assertEquals(6.0, homeWork.calculate("( 8 + 2 * 5 ) / ( 1 + 3 * 2 - 4 )"));
    }

}