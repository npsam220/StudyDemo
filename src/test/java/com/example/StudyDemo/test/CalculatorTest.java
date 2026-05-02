package com.example.StudyDemo.test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator(); // 各テストの実行前に初期化する（かくテストのじっこうまえにしょきかする）
    }

     @Test
    void testAdd() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void testDivide() {
        assertEquals(5, calculator.divide(10, 2));
    }

    @Test
    void testDivideByZero() {
        Exception exception = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(10, 0)
        );

        assertEquals("ゼロで割ることはできません", exception.getMessage());
    }
}
