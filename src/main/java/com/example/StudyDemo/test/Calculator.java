package com.example.StudyDemo.test;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("ゼロで割ることはできません");
        return a / b;
    }
}
